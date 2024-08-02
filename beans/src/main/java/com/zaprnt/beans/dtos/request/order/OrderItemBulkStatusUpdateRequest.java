package com.zaprnt.beans.dtos.request.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class OrderItemBulkStatusUpdateRequest {
    @NotBlank
    private String orderId;
    @NotEmpty
    @Valid
    private List<OrderItemStatusRequest> orderItemStatusList;
}
