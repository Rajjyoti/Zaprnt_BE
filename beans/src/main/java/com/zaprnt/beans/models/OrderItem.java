package com.zaprnt.beans.models;

import com.zaprnt.beans.common.mongo.BaseMongoBean;
import com.zaprnt.beans.enums.OrderStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
@Document
@EqualsAndHashCode(callSuper = true)
public class OrderItem extends BaseMongoBean {
    @Indexed
    private String orderId;
    private String productId;
    private String productName;
    private String productImageUrl;
    private int quantity;
    private BigDecimal price;
    private OrderStatus status;
}
