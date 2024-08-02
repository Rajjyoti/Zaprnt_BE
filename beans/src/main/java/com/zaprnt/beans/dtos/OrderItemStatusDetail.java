package com.zaprnt.beans.dtos;

import com.zaprnt.beans.enums.OrderStatus;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class OrderItemStatusDetail {
    private OrderStatus status;
    private int count;
}
