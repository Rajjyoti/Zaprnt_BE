package com.zaprnt.beans.dtos.request.cart;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class CartItemRequest {
    @NotBlank
    private String productId;
    @NotBlank
    private String userId;
    private int quantity;
    @NotNull
    private BigDecimal price;
}
