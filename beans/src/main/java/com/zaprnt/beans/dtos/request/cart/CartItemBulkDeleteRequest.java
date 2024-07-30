package com.zaprnt.beans.dtos.request.cart;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class CartItemBulkDeleteRequest {
    List<String> cartItemIds;
}
