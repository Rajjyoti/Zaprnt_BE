package com.zaprent.helper;

import com.zaprnt.beans.enums.OrderStatus;

public interface IOrderStatusHelper {
    void updateOrderStatusFromOrderItems(String orderId, OrderStatus status);
}
