package com.zaprent.service.wishList;

import com.zaprent.repo.wishList.IWishListRepo;
import com.zaprent.service.product.IProductService;
import com.zaprnt.beans.dtos.request.wishList.WishListRequest;
import com.zaprnt.beans.dtos.response.product.ProductResponse;
import com.zaprnt.beans.dtos.response.wishList.WishListResponse;
import com.zaprnt.beans.error.ZError;
import com.zaprnt.beans.error.ZException;
import com.zaprnt.beans.models.WishList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.zaprent.utils.WishListUtils.*;
import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class WishListService {
    private final IWishListRepo wishListRepo;
    private final IProductService productService;

    public WishListResponse addToWishList(WishListRequest request) {
        ProductResponse product = productService.getProductById(request.getProductId());
        WishList wishList = wishListRepo.saveWishList(convertToWishList(request));
        return convertToWishListResponse(wishList, product);
    }

    public WishListResponse getWishListItemById(String id) {
        WishList wishList = wishListRepo.getWishListItemById(id);
        if (isNull(wishList)) {
            throw new ZException(ZError.WISHLIST_ITEM_NOT_FOUND);
        }
        ProductResponse product = productService.getProductById(wishList.getProductId());
        return convertToWishListResponse(wishList, product);
    }

    public List<WishListResponse> getWishListByUserId(String userId) {
        List<WishList> wishLists = wishListRepo.getWishListByUserId(userId);
        List<ProductResponse> products = productService.getProductsByIds(wishLists.stream().map(WishList::getProductId).toList());
        return convertToWishListResponseList(wishLists, products);
    }

    public boolean removeFromWishList(String id) {
        return wishListRepo.removeFromWishList(id);
    }
}
