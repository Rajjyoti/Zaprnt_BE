package com.zaprnt.beans.dtos.request.cart;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class CartItemUpdateRequest {
    @NotBlank
    private String cartId;
    private int quantity;
}
