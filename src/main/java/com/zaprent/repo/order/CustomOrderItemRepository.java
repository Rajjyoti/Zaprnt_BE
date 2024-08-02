package com.zaprent.repo.order;

import com.zaprnt.beans.dtos.OrderItemStatusDetail;
import com.zaprnt.beans.enums.OrderStatus;
import com.zaprnt.beans.models.OrderItem;

import java.util.List;

public interface CustomOrderItemRepository {
    OrderItem saveOrderItem(OrderItem orderItem);
    void saveOrderItems(List<OrderItem> orderItems);
    OrderItem getOrderItemById(String orderItemId);
    List<OrderItem> getOrderItemsByOrderId(String orderId);
    List<OrderItem> getOrderItemsByIds(String orderId, List<String> itemIds);
    List<OrderItemStatusDetail> getOrderItemStatusDetailsForOrder(String orderId);
    OrderStatus getLowestOrderStatusFromOrderItems(String orderId);
    boolean deleteOrderItem(String id);
}
