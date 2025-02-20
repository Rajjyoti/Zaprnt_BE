package com.zaprnt.beans.dtos.request.order;

import com.zaprnt.beans.enums.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OrderUpdateRequest {
    @NotBlank
    private String orderId;
    @NotNull
    private OrderStatus status;
}
