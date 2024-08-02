package com.zaprent.service.order.impl;

import com.zaprent.helper.IOrderStatusHelper;
import com.zaprent.repo.order.IOrderItemRepo;
import com.zaprent.service.order.IOrderItemService;
import com.zaprnt.beans.dtos.OrderItemStatusDetail;
import com.zaprnt.beans.dtos.request.order.OrderItemBulkStatusUpdateRequest;
import com.zaprnt.beans.dtos.request.order.OrderItemStatusRequest;
import com.zaprnt.beans.dtos.response.order.OrderStatusDetailsResponse;
import com.zaprnt.beans.enums.OrderStatus;
import com.zaprnt.beans.error.ZError;
import com.zaprnt.beans.error.ZException;
import com.zaprnt.beans.models.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements IOrderItemService {

    private final IOrderItemRepo orderItemRepo;
    private final IOrderStatusHelper orderStatusHelper;

    @Override
    public OrderItem getOrderItemById(String id) {
        OrderItem item = orderItemRepo.getOrderItemById(id);
        if (isNull(item)) {
            throw new ZException(ZError.ORDER_ITEM_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return item;
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(String orderId) {
        return orderItemRepo.getOrderItemsByOrderId(orderId);
    }

    @Override
    public OrderStatusDetailsResponse getOrderItemStatusDetailsForOrder(String orderId) {
        List<OrderItemStatusDetail> statusDetails = orderItemRepo.getOrderItemStatusDetailsForOrder(orderId);
        return new OrderStatusDetailsResponse()
                .setOrderId(orderId)
                .setOrderStatusDetails(statusDetails);
    }

    @Override
    public OrderItem createOrderItem(OrderItem item) {
        return orderItemRepo.saveOrderItem(item);
    }

    @Override
    public void createOrderItems(List<OrderItem> items) {
        orderItemRepo.saveOrderItems(items);
    }

    @Override
    public OrderItem updateOrderItemStatus(OrderItemStatusRequest request) {
        OrderItem item = getOrderItemById(request.getOrderItemId());
        item.setStatus(request.getStatus());
        OrderItem orderItem = orderItemRepo.saveOrderItem(item);
        updateOrderStatusFromOrderItems(request.getOrderId());
        return orderItem;
    }

    @Override
    public void updateBulkOrderItemStatus(OrderItemBulkStatusUpdateRequest request) {
        List<String> itemIds = request.getOrderItemStatusList().stream().map(OrderItemStatusRequest::getOrderItemId).toList();
        Map<String, OrderItemStatusRequest> statusRequestById = request.getOrderItemStatusList().stream().collect(Collectors.toMap(OrderItemStatusRequest::getOrderItemId, s -> s));
        List<OrderItem> orderItems = orderItemRepo.getOrderItemsByIds(request.getOrderId(), itemIds);
        for (OrderItem item: orderItems) {
            if (isNull(item) || isNull(statusRequestById.get(item.getId()))) {
                continue;
            }
            item.setStatus(statusRequestById.get(item.getId()).getStatus());
        }
        orderItemRepo.saveOrderItems(orderItems);
        updateOrderStatusFromOrderItems(request.getOrderId());
    }

    private void updateOrderStatusFromOrderItems(String orderId) {
        OrderStatus status = orderItemRepo.getLowestOrderStatusFromOrderItems(orderId);
        orderStatusHelper.updateOrderStatusFromOrderItems(orderId, status);
    }

}
