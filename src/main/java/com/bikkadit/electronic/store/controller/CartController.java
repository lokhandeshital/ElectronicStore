package com.bikkadit.electronic.store.controller;

import com.bikkadit.electronic.store.dtos.AddItemToCartRequest;
import com.bikkadit.electronic.store.dtos.ApiResponse;
import com.bikkadit.electronic.store.dtos.CartDto;
import com.bikkadit.electronic.store.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts/")
public class CartController {

    @Autowired
    private CartService cartService;

    // Add item to cart
    @PostMapping("/{userId}")
    public ResponseEntity<CartDto> addItemToCart(@PathVariable String userId, @RequestBody AddItemToCartRequest request) {

        CartDto cartDto = cartService.addItemToCart(userId, request);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);

    }

    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable String userId, @PathVariable Integer itemId) {

        cartService.removeItemFromCart(userId, itemId);
        ApiResponse response = ApiResponse.builder()
                .message("Item is Removed")
                .success(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Clear Cart
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable String userId) {
        cartService.clearCart(userId);
        ApiResponse response = ApiResponse.builder()
                .message("Cart is Cleared")
                .success(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //getCart
    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCart(@PathVariable String userId) {

        CartDto cartDto = cartService.getCartByUser(userId);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }


}
