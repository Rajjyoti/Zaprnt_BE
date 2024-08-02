package com.zaprent.helper.impl;

import com.zaprent.helper.IOrderStatusHelper;
import com.zaprent.service.order.IOrderService;
import com.zaprnt.beans.dtos.request.order.OrderUpdateRequest;
import com.zaprnt.beans.enums.OrderStatus;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class OrderStatusHelperImpl implements IOrderStatusHelper {
    private final IOrderService orderService;
    public OrderStatusHelperImpl(@Lazy IOrderService orderService) {
        this.orderService = orderService;
    }
    @Override
    public void updateOrderStatusFromOrderItems(String orderId, OrderStatus status) {
        orderService.updateOrderStatus(new OrderUpdateRequest().setOrderId(orderId).setStatus(status));
    }
}
