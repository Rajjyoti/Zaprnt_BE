package com.zaprnt.beans.dtos.request.cart;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class CartItemRequest {
    @NotBlank
    private String productId;
    @NotBlank
    private String userId;
    private int quantity;
}
