package com.zaprnt.beans.dtos.response.order;

import com.zaprnt.beans.enums.RentType;
import com.zaprnt.beans.models.OrderItem;
import com.zaprnt.beans.enums.OrderStatus;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Data
@Accessors(chain = true)
public class OrderResponse {
    private String orderId;
    private String userId;
    private List<OrderItem> items;
    private BigDecimal totalPrice;
    private OrderStatus status;
    private RentType rentType;
    private long startTime;
    private long endTime;
    private long createdTime;
    private long modifiedTime;
}
