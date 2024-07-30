package com.zaprnt.beans.dtos.request.cart;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class CartItemUpdateRequest {
    @NotBlank
    private String cartId;
    private int quantity;
    private BigDecimal price;
}
