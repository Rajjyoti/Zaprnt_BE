package com.zaprnt.beans.dtos.response.order;

import com.zaprnt.beans.dtos.OrderItemStatusDetail;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class OrderStatusDetailsResponse {
    private String orderId;
    private List<OrderItemStatusDetail> orderStatusDetails;
}
