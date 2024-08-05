package com.zaprent.utils;

import com.zaprnt.beans.dtos.request.wishList.WishListRequest;
import com.zaprnt.beans.dtos.response.product.ProductResponse;
import com.zaprnt.beans.dtos.response.wishList.WishListResponse;
import com.zaprnt.beans.models.WishList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.zaprent.utils.CartItemUtils.getProductDetail;
import static com.zaprnt.beans.common.util.CommonUtils.nullSafeCollection;
import static java.util.Objects.isNull;

public class WishListUtils {

    public static WishList convertToWishList(WishListRequest request) {
        return new WishList().setProductId(request.getProductId()).setUserId(request.getUserId());
    }

    public static List<WishListResponse> convertToWishListResponseList(List<WishList> wishLists, List<ProductResponse> products) {
        List<WishListResponse> responses = new ArrayList<>();
        Map<String, ProductResponse> productById = products.stream().collect(Collectors.toMap(ProductResponse::getId, p -> p));
        for (WishList wishList: nullSafeCollection(wishLists)) {
            if (isNull(wishList) || isNull(productById.get(wishList.getProductId()))) {
                continue;
            }
            responses.add(convertToWishListResponse(wishList, productById.get(wishList.getProductId())));
        }
        return responses;
    }

    public static WishListResponse convertToWishListResponse(WishList wishList, ProductResponse product) {
        return new WishListResponse()
                .setId(wishList.getId())
                .setUserId(wishList.getUserId())
                .setProductId(product.getId())
                .setProductDetail(getProductDetail(product));
    }
}
