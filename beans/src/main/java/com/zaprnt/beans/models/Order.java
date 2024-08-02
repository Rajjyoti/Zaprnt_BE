package com.zaprnt.beans.models;

import com.zaprnt.beans.common.mongo.BaseMongoBean;
import com.zaprnt.beans.enums.OrderStatus;
import com.zaprnt.beans.enums.RentType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
@Document
@EqualsAndHashCode(callSuper = true)
public class Order extends BaseMongoBean {
    private String userId;
    private BigDecimal totalPrice;
    private RentType rentType;
    private OrderStatus status;
    private int itemCount;
    private long startTime;
    private long endTime;
}
