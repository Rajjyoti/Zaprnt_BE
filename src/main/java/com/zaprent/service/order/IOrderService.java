package com.zaprent.service.order;

import com.zaprnt.beans.dtos.request.order.OrderCreateRequest;
import com.zaprnt.beans.dtos.request.order.OrderUpdateRequest;
import com.zaprnt.beans.dtos.response.order.OrderResponse;

import java.util.List;

public interface IOrderService {
    OrderResponse getOrderById(String orderId);
    List<OrderResponse> getOrdersByUserId(String userId);
    OrderResponse createOrder(OrderCreateRequest request);
    OrderResponse updateOrderStatus(OrderUpdateRequest request);
}
