package com.zaprent.repo.wishList;

import com.zaprnt.beans.models.WishList;

import java.util.List;

public interface CustomWishListRepository {
    WishList saveWishList(WishList wishList);
    List<WishList> getWishListByUserId(String userId);
    WishList getWishListItemById(String id);
    boolean removeFromWishList(String id);
}
