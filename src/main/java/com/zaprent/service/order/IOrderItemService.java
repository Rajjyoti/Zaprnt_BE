package com.zaprent.service.order;

import com.zaprnt.beans.dtos.request.order.OrderItemBulkStatusUpdateRequest;
import com.zaprnt.beans.dtos.request.order.OrderItemStatusRequest;
import com.zaprnt.beans.dtos.response.order.OrderStatusDetailsResponse;
import com.zaprnt.beans.models.OrderItem;

import java.util.List;

public interface IOrderItemService {
    OrderItem getOrderItemById(String id);
    List<OrderItem> getOrderItemsByOrderId(String orderId);
    OrderStatusDetailsResponse getOrderItemStatusDetailsForOrder(String orderId);
    OrderItem createOrderItem(OrderItem item);

    void createOrderItems(List<OrderItem> items);

    OrderItem updateOrderItemStatus(OrderItemStatusRequest request);

    void updateBulkOrderItemStatus(OrderItemBulkStatusUpdateRequest request);
}
