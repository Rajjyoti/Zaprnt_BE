package com.zaprnt.beans.dtos;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class OrderItem {
    private String productId;
    private String productName;
    private String productImageUrl;
    private int quantity;
    private BigDecimal price;
}
