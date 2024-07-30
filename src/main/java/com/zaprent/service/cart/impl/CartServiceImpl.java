package com.zaprent.service.cart.impl;

import com.zaprent.repo.cart.ICartRepo;
import com.zaprent.service.cart.ICartService;
import com.zaprent.service.product.IProductService;
import com.zaprnt.beans.dtos.request.cart.CartItemBulkDeleteRequest;
import com.zaprnt.beans.dtos.request.cart.CartItemRequest;
import com.zaprnt.beans.dtos.request.cart.CartItemUpdateRequest;
import com.zaprnt.beans.dtos.response.cart.CartItemResponse;
import com.zaprnt.beans.dtos.response.product.ProductResponse;
import com.zaprnt.beans.error.ZError;
import com.zaprnt.beans.error.ZException;
import com.zaprnt.beans.models.CartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.zaprent.utils.CartItemUtils.*;
import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements ICartService {
    private final ICartRepo cartRepo;
    private final IProductService productService;

    @Override
    public CartItemResponse saveCartItem(CartItemRequest request) {
        CartItem cartItem = cartRepo.saveCartItem(convertToCartItem(request));
        ProductResponse product = productService.getProductById(cartItem.getProductId());
        return convertToCartItemResponse(cartItem, product);
    }

    @Override
    public CartItemResponse updateCartItem(CartItemUpdateRequest request) {
        CartItem cartItem = cartRepo.getCartItemById(request.getCartId());
        if (isNull(cartItem)) {
            throw new ZException(ZError.CART_ITEM_NOT_FOUND, request.getCartId());
        }
        ProductResponse product = productService.getProductById(cartItem.getProductId());
        updateCartItemFields(cartItem, request);
        cartRepo.saveCartItem(cartItem);
        return convertToCartItemResponse(cartItem, product);
    }

    @Override
    public List<CartItemResponse> getCartItemsByUserId(String userId) {
        List<CartItem> cartItems = cartRepo.getCartItemsByUserId(userId);
        if (isNull(cartItems) || cartItems.isEmpty()) {
            throw new ZException(ZError.CART_ITEMS_NOT_FOUND, HttpStatus.NOT_FOUND, userId);
        }
        List<String> productIds = cartItems.stream().map(CartItem::getProductId).toList();
        List<ProductResponse> products = productService.getProductsByIds(productIds);
        Map<String, ProductResponse> productsById = products.stream().collect(Collectors.toMap(ProductResponse::getId, product -> product));
        return convertToCartItemResponseList(cartItems, productsById);
    }

    @Override
    public long removeCartItems(String userId, CartItemBulkDeleteRequest request) {
        return cartRepo.removeCartItems(userId, request.getCartItemIds());
    }
}
