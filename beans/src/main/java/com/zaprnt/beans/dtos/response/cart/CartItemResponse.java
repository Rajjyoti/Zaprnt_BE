package com.zaprnt.beans.dtos.response.cart;

import com.zaprnt.beans.dtos.request.ProductDetail;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class CartItemResponse {
    private String productId;
    private String userId;
    private ProductDetail productDetail;
    private int quantity;
    private BigDecimal price;
    private long createdTime;
    private long modifiedTime;
}
