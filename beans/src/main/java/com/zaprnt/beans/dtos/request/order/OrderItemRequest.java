package com.zaprnt.beans.dtos.request.order;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OrderItemRequest {
    @NotBlank
    private String productId;
    private int quantity;
}
