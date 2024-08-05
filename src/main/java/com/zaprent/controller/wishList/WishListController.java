package com.zaprent.controller.wishList;

import com.zaprent.service.wishList.WishListService;
import com.zaprnt.beans.dtos.request.wishList.WishListRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.zaprnt.beans.error.ZResponseEntityBuilder.okCreatedResponseEntity;
import static com.zaprnt.beans.error.ZResponseEntityBuilder.okResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wishlist")
public class WishListController {
    private final WishListService wishListService;
    @GetMapping("/user-ids/{userId}")
    public ResponseEntity<Map<String, Object>> getWishListByUserId(@PathVariable String userId) {
        return okResponseEntity(wishListService.getWishListByUserId(userId));
    }

    @GetMapping("/ids/{id}")
    public ResponseEntity<Map<String, Object>> getWishListItemById(@PathVariable String id) {
        return okCreatedResponseEntity(wishListService.getWishListItemById(id));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> addToWishList(@Valid @RequestBody WishListRequest request) {
        return okCreatedResponseEntity(wishListService.addToWishList(request));
    }

    @DeleteMapping
    public ResponseEntity<Map<String, Object>> removeFromWishList(@PathVariable String id) {
        return okCreatedResponseEntity(wishListService.removeFromWishList(id));
    }
}
