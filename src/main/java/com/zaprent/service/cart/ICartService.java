package com.zaprent.service.cart;

import com.zaprnt.beans.dtos.request.cart.CartItemBulkDeleteRequest;
import com.zaprnt.beans.dtos.request.cart.CartItemRequest;
import com.zaprnt.beans.dtos.request.cart.CartItemUpdateRequest;
import com.zaprnt.beans.dtos.response.cart.CartItemResponse;

import java.util.List;

public interface ICartService {
    CartItemResponse saveCartItem(CartItemRequest request);

    CartItemResponse updateCartItem(CartItemUpdateRequest request);

    List<CartItemResponse> getCartItemsByUserId(String userId);

    long removeCartItems(String userId, CartItemBulkDeleteRequest request);
}
