package com.zaprent.utils;

import com.zaprnt.beans.dtos.request.ProductDetail;
import com.zaprnt.beans.dtos.request.cart.CartItemRequest;
import com.zaprnt.beans.dtos.request.cart.CartItemUpdateRequest;
import com.zaprnt.beans.dtos.response.cart.CartItemResponse;
import com.zaprnt.beans.dtos.response.product.ProductResponse;
import com.zaprnt.beans.models.CartItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class CartItemUtils {
    public static CartItemResponse convertToCartItemResponse(CartItem cartItem, ProductResponse product) {
        if (isNull(cartItem)) {
            return null;
        }
        return new CartItemResponse()
                .setProductId(cartItem.getProductId())
                .setUserId(cartItem.getUserId())
                .setQuantity(cartItem.getQuantity())
                .setPrice(cartItem.getPrice())
                .setProductDetail(getProductDetail(product))
                .setCreatedTime(cartItem.getCreatedTime())
                .setModifiedTime(cartItem.getModifiedTime());
    }

    public static ProductDetail getProductDetail(ProductResponse product) {
        if (isNull(product)) {
            return null;
        }
        return new ProductDetail()
                .setTitle(product.getTitle())
                .setPrice(product.getPrice())
                .setAttributes(product.getAttributes())
                .setCategory(product.getCategory())
                .setCondition(product.getCondition())
                .setDescription(product.getDescription())
                .setStatus(product.getStatus())
                .setSubCategory(product.getSubCategory())
                .setImages(product.getImages());
    }

    public static CartItem convertToCartItem(CartItemRequest request) {
        if (isNull(request)) {
            return null;
        }
        return new CartItem()
                .setProductId(request.getProductId())
                .setUserId(request.getUserId())
                .setQuantity(request.getQuantity())
                .setPrice(request.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
    }

    public static void updateCartItemFields(CartItem cartItem, CartItemUpdateRequest request) {
        if (isNull(request)) {
            return;
        }
        if (request.getQuantity() != 0) {
            cartItem.setQuantity(request.getQuantity());
        }
        if (nonNull(request.getPrice())) {
            cartItem.setPrice(request.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
        }
    }

    public static List<CartItemResponse> convertToCartItemResponseList(List<CartItem> cartItems, Map<String, ProductResponse> productsById) {
        List<CartItemResponse> responses = new ArrayList<>();
        for (CartItem item: cartItems) {
            if (isNull(item)) {
                continue;
            }
            ProductResponse product = productsById.get(item.getProductId());
            CartItemResponse response = convertToCartItemResponse(item, product);
            if (nonNull(response)) {
                responses.add(response);
            }
        }
        return responses;
    }
}
