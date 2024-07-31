package com.zaprent.service.order.impl;

import com.zaprent.repo.order.IOrderRepo;
import com.zaprent.service.order.IOrderService;
import com.zaprent.service.product.IProductService;
import com.zaprnt.beans.dtos.OrderItem;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.zaprent.utils.OrderUtils.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {
    private final IOrderRepo orderRepo;
    private final IProductService productService;

    @Override
    public OrderResponse getOrderById(String orderId) {
        Order order = orderRepo.getOrderById(orderId);
        if (isNull(order)) {
            throw new ZException(ZError.ORDER_NOT_FOUND, HttpStatus.NOT_FOUND, orderId);
        }
        return convertToOrderResponse(order);
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
        List<OrderItem> orderItems = getOrderItemsFromProducts(request.getItems());
        Order order = orderRepo.saveOrder(convertToOrder(orderItems, request, OrderStatus.PENDING_APPROVAL));
        //TODO: Notification to renters for approval of each order item
        //TODO: send kafka event to products to update the quantity and availability status
        return convertToOrderResponse(order);
    }

    @Override
    public OrderResponse updateOrderStatus(OrderUpdateRequest request) {
        Order order = orderRepo.getOrderById(request.getOrderId());
        if (isNull(order)) {
            throw new ZException(ZError.ORDER_NOT_FOUND, request.getOrderId());
        }
        order.setStatus(request.getStatus());
        return convertToOrderResponse(orderRepo.saveOrder(order));
    }

    private List<OrderItem> getOrderItemsFromProducts(List<OrderItemRequest> items) {
        List<String> productIds = items.stream().map(OrderItemRequest::getProductId).toList();
        Map<String, OrderItemRequest> orderItemByProductId = items.stream().collect(Collectors.toMap(OrderItemRequest::getProductId, orderItem -> orderItem));
        List<ProductResponse> products = productService.getProductsByIds(productIds);
        List<OrderItem> orderItems = new ArrayList<>();
        for (ProductResponse product: products) {
            if (isNull(product) || isNull(orderItemByProductId.get(product.getId()))) {
                continue;
            }
            OrderItem item = convertToOrderItemFromProduct(product, orderItemByProductId.get(product.getId()));
            if (nonNull(item)) {
                orderItems.add(item);
            }
        }
        return orderItems;
    }
}
