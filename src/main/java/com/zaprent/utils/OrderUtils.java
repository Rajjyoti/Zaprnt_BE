package com.zaprent.utils;

import com.zaprnt.beans.models.OrderItem;
import com.zaprnt.beans.dtos.request.order.OrderCreateRequest;
import com.zaprnt.beans.dtos.request.order.OrderItemRequest;
import com.zaprnt.beans.dtos.response.order.OrderResponse;
import com.zaprnt.beans.dtos.response.product.ProductResponse;
import com.zaprnt.beans.enums.OrderStatus;
import com.zaprnt.beans.models.Order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class OrderUtils {
    public static OrderResponse convertToOrderResponse(Order order, List<OrderItem> orderItems) {
        return new OrderResponse()
                .setOrderId(order.getId())
                .setItems(orderItems)
                .setStatus(order.getStatus())
                .setTotalPrice(order.getTotalPrice())
                .setUserId(order.getUserId())
                .setRentType(order.getRentType())
                .setStartTime(order.getStartTime())
                .setEndTime(order.getEndTime())
                .setCreatedTime(order.getCreatedTime())
                .setModifiedTime(order.getModifiedTime());
    }

    public static List<OrderResponse> convertToOrderResponseList(List<Order> orders) {
        List<OrderResponse> orderResponses = new ArrayList<>();
        for (Order order : orders) {
            if (isNull(order)) {
                continue;
            }
            OrderResponse response = convertToOrderResponse(order, null);
            if (nonNull(response)) {
                orderResponses.add(response);
            }
        }
        return orderResponses;
    }

    public static Order convertToOrder(OrderCreateRequest request, OrderStatus status, BigDecimal totalPrice, int itemCount, String orderId) {
        Order order = new Order();
        order.setId(orderId);
        order.setRentType(request.getRentType())
                .setItemCount(itemCount)
                .setStartTime(request.getStartTime())
                .setEndTime(request.getEndTime())
                .setStatus(status)
                .setUserId(request.getUserId())
                .setTotalPrice(totalPrice);
        return order;
    }

    public static BigDecimal calculateTotalOrderPrice(List<OrderItem> items) {
        return items.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static OrderItem convertToOrderItemFromProduct(ProductResponse product, OrderItemRequest orderItemRequest, String orderId, OrderStatus status) {
        if (product == null || orderItemRequest == null) {
            return null;
        }
        OrderItem item = new OrderItem();
        item.setId(UUID.randomUUID().toString());
        item.setOrderId(orderId)
                .setProductId(product.getId())
                .setPrice(product.getPrice())
                .setProductName(product.getTitle())
                .setProductImageUrl(product.getImages().get(0))
                .setQuantity(orderItemRequest.getQuantity())
                .setStatus(status);
        return item;
    }
}
