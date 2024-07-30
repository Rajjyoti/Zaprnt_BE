package com.zaprent.utils;

import com.zaprnt.beans.dtos.OrderItem;
import com.zaprnt.beans.dtos.request.order.OrderCreateRequest;
import com.zaprnt.beans.dtos.request.order.OrderItemRequest;
import com.zaprnt.beans.dtos.response.order.OrderResponse;
import com.zaprnt.beans.dtos.response.product.ProductResponse;
import com.zaprnt.beans.enums.OrderStatus;
import com.zaprnt.beans.models.Order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class OrderUtils {
    public static OrderResponse convertToOrderResponse(Order order) {
        return new OrderResponse()
                .setOrderId(order.getId())
                .setItems(order.getItems())
                .setStatus(order.getStatus())
                .setTotalPrice(order.getTotalPrice())
                .setUserId(order.getUserId())
                .setCreatedTime(order.getCreatedTime())
                .setModifiedTime(order.getModifiedTime());
    }

    public static List<OrderResponse> convertToOrderResponseList(List<Order> orders) {
        List<OrderResponse> orderResponses = new ArrayList<>();
        for (Order order: orders) {
            if (isNull(order)) {
                continue;
            }
            OrderResponse response = convertToOrderResponse(order);
            if (nonNull(response)) {
                orderResponses.add(response);
            }
        }
        return orderResponses;
    }

    public static Order convertToOrder(List<OrderItem> orderItems, OrderCreateRequest request, OrderStatus status) {
        return new Order()
                .setItems(orderItems)
                .setStatus(status)
                .setUserId(request.getUserId())
                .setTotalPrice(calculateTotalOrderPrice(orderItems));
    }

    public static BigDecimal calculateTotalOrderPrice(List<OrderItem> items) {
        return items.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static OrderItem convertToOrderItemFromProduct(ProductResponse product, OrderItemRequest orderItemRequest) {
        if (product == null || orderItemRequest == null) {
            return null;
        }
        return new OrderItem()
                .setProductId(product.getId())
                .setPrice(product.getPrice())
                .setProductName(product.getTitle())
                .setProductImageUrl(product.getImages().get(0))
                .setQuantity(orderItemRequest.getQuantity());
    }
}
