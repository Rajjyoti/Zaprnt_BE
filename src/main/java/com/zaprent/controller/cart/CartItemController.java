package com.zaprent.controller.cart;

import com.zaprent.service.cart.ICartService;
import com.zaprnt.beans.dtos.request.cart.CartItemBulkDeleteRequest;
import com.zaprnt.beans.dtos.request.cart.CartItemRequest;
import com.zaprnt.beans.dtos.request.cart.CartItemUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.zaprnt.beans.error.ZResponseEntityBuilder.okCreatedResponseEntity;
import static com.zaprnt.beans.error.ZResponseEntityBuilder.okResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartItemController {
    private final ICartService cartService;
    @GetMapping("/user-ids/{id}")
    public ResponseEntity<Map<String, Object>> getCartItemsByUserId(@PathVariable String id) {
        return okResponseEntity(cartService.getCartItemsByUserId(id));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> saveCartItem(@RequestBody CartItemRequest request) {
        return okCreatedResponseEntity(cartService.saveCartItem(request));
    }

    @PutMapping
    public ResponseEntity<Map<String, Object>> updateCartItem(@RequestBody CartItemUpdateRequest request) {
        return okResponseEntity(cartService.updateCartItem(request));
    }

    @DeleteMapping("/delete/user-ids/{userId}")
    public ResponseEntity<Map<String, Object>> removeCartItems(@PathVariable String userId, @RequestBody CartItemBulkDeleteRequest request) {
        return okResponseEntity(cartService.removeCartItems(userId, request));
    }
}
