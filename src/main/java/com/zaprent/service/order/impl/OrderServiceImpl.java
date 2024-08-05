package com.zaprent.service.order.impl;

import com.zaprent.kafka.KafkaProducer;
import com.zaprent.repo.order.IOrderRepo;
import com.zaprent.service.order.IOrderItemService;
import com.zaprent.service.order.IOrderService;
import com.zaprent.service.product.IProductService;
import com.zaprnt.beans.kafka.BulkProductUpdateEvent;
import com.zaprnt.beans.kafka.ProductUpdateEvent;
import com.zaprnt.beans.models.OrderItem;
import com.zaprnt.beans.dtos.request.order.OrderCreateRequest;
import com.zaprnt.beans.dtos.request.order.OrderItemRequest;
import com.zaprnt.beans.dtos.request.order.OrderUpdateRequest;
import com.zaprnt.beans.dtos.response.order.OrderResponse;
import com.zaprnt.beans.dtos.response.product.ProductResponse;
import com.zaprnt.beans.enums.OrderStatus;
import com.zaprnt.beans.error.ZError;
import com.zaprnt.beans.error.ZException;
import com.zaprnt.beans.models.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.zaprent.utils.OrderUtils.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {
    private final IOrderRepo orderRepo;
    private final IProductService productService;
    private final IOrderItemService orderItemService;
    private final KafkaProducer kafkaProducer;
    private final AsyncTaskExecutor asyncTaskExecutor;

    @Override
    public OrderResponse getOrderById(String orderId) {
        Order order = orderRepo.getOrderById(orderId);
        if (isNull(order)) {
            throw new ZException(ZError.ORDER_NOT_FOUND, HttpStatus.NOT_FOUND, orderId);
        }
        return convertToOrderResponse(order, null);
    }

    @Override
    public List<OrderResponse> getOrdersByUserId(String userId) {
        List<Order> orders = orderRepo.getOrdersByUserId(userId);
        if (isNull(orders) || orders.isEmpty()) {
            throw new ZException(ZError.ORDERS_NOT_FOUND, HttpStatus.NOT_FOUND, userId);
        }
        return convertToOrderResponseList(orders);
    }

    @Override
    public OrderResponse createOrder(OrderCreateRequest request) {
        String orderId = UUID.randomUUID().toString();
        List<ProductUpdateEvent> updateEvents = new ArrayList<>();
        List<OrderItem> orderItems = getOrderItemsFromProducts(request.getItems(), orderId, OrderStatus.PENDING_APPROVAL, updateEvents);
        Order order = orderRepo.saveOrder(convertToOrder(request, OrderStatus.PENDING_APPROVAL, calculateTotalOrderPrice(orderItems), request.getItems().size(), orderId));
        orderItemService.createOrderItems(orderItems);
        //TODO: Notification to renters for approval of each order item
        asyncTaskExecutor.execute(() -> kafkaProducer.publishEventForProductUpdate(new BulkProductUpdateEvent(updateEvents)));
        return convertToOrderResponse(order, orderItems);
    }

    @Override
    public OrderResponse updateOrderStatus(OrderUpdateRequest request) {
        Order order = orderRepo.getOrderById(request.getOrderId());
        if (isNull(order)) {
            throw new ZException(ZError.ORDER_NOT_FOUND, request.getOrderId());
        }
        order.setStatus(request.getStatus());
        return convertToOrderResponse(orderRepo.saveOrder(order), null);
    }

    private List<OrderItem> getOrderItemsFromProducts(List<OrderItemRequest> items, String orderId, OrderStatus status, List<ProductUpdateEvent> updateEvents) {
        List<String> productIds = items.stream().map(OrderItemRequest::getProductId).toList();
        Map<String, OrderItemRequest> orderItemByProductId = items.stream().collect(Collectors.toMap(OrderItemRequest::getProductId, orderItem -> orderItem));
        List<ProductResponse> products = productService.getProductsByIds(productIds);
        List<OrderItem> orderItems = new ArrayList<>();
        for (ProductResponse product : products) {
            if (isNull(product)) {
                continue;
            }
            OrderItemRequest itemRequest = orderItemByProductId.get(product.getId());
            if (isNull(itemRequest)) {
                continue;
            }
            if (product.getQuantity() < itemRequest.getQuantity()) {
                throw new ZException(ZError.PRODUCT_QUANTITY_IS_LESS_THAN_REQUIRED_QUANTITY, String.valueOf(product.getQuantity()), String.valueOf(itemRequest.getQuantity()));
            }
            OrderItem item = convertToOrderItemFromProduct(product, itemRequest, orderId, status);
            if (nonNull(item)) {
                orderItems.add(item);
                updateEvents.add(new ProductUpdateEvent(product.getId(), product.getQuantity() - item.getQuantity()));
            }
        }
        return orderItems;
    }
}
