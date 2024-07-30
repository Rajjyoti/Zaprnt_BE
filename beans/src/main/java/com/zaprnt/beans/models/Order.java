package com.zaprnt.beans.models;

import com.zaprnt.beans.common.BaseMongoBean;
import com.zaprnt.beans.dtos.OrderItem;
import com.zaprnt.beans.enums.OrderStatus;
import com.zaprnt.beans.enums.RentType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Data
@Accessors(chain = true)
@Document
@EqualsAndHashCode(callSuper = true)
public class Order extends BaseMongoBean {
    private String userId;
    private List<OrderItem> items;
    private BigDecimal totalPrice;
    private RentType rentType;
    private OrderStatus status;
    private long startTime;
    private long endTime;
}
