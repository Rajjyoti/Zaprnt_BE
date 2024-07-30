package com.zaprent.repo.cart;

import com.zaprnt.beans.models.CartItem;

import java.util.List;

public interface CustomCartRepository {
    CartItem saveCartItem(CartItem item);
    CartItem getCartItemById(String cartId);
    CartItem getCartItemByProductIdAndUserId(String userId, String productId);
    List<CartItem> getCartItemsByUserId(String userId);
    boolean removeCartItemById(String id);
    long removeCartItems(String userId, List<String> cartItemIds);
}
