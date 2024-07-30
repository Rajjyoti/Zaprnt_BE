package com.zaprent.repo.order;

import com.zaprnt.beans.models.Order;

import java.util.List;

public interface CustomOrderRepository {
    Order saveOrder(Order order);
    Order getOrderById(String orderId);
    List<Order> getOrdersByUserId(String userId);
    boolean deleteOrder(String id);
}
