package com.bikkadit.electronic.store.controller;

import com.bikkadit.electronic.store.dtos.AddItemToCartRequest;
import com.bikkadit.electronic.store.dtos.ApiResponse;
import com.bikkadit.electronic.store.dtos.CartDto;
import com.bikkadit.electronic.store.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts/")
@Slf4j
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * @apiNote This Api is Used to Add Item To Cart
     * @param userId
     * @param request
     * @return
     */
    // Add item to cart
    @PostMapping("/{userId}")
    public ResponseEntity<CartDto> addItemToCart(@PathVariable String userId, @RequestBody AddItemToCartRequest request) {

        log.info("Initiated Request for Add Item to Cart with userId : {}", userId);
        CartDto cartDto = cartService.addItemToCart(userId, request);
        log.info("Completed Request for Add Item to Cart with userId : {}", userId);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);

    }

    /**
     * @apiNote This Api is Used to Remove Item From Cart
     * @param userId
     * @param itemId
     * @return
     */
    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable String userId, @PathVariable Integer itemId) {
        log.info("Initiated Request for Remove Item From Cart with userId : {}", userId);
        cartService.removeItemFromCart(userId, itemId);
        ApiResponse response = ApiResponse.builder()
                .message("Item is Removed")
                .success(true)
                .build();
        log.info("Completed Request for Remove Item From Cart with userId : {}", userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * @apiNote This Api is Used to Clear Cart
     * @param userId
     * @return
     */
    //Clear Cart
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable String userId) {
        log.info("Initiated Request for Clear Cart with userId : {}", userId);
        cartService.clearCart(userId);
        ApiResponse response = ApiResponse.builder()
                .message("Cart is Cleared")
                .success(true)
                .build();
        log.info("Completed Request for Clear Cart with userId : {}", userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * @apiNote This Api is Used to Get Cart
     * @param userId
     * @return
     */
    //getCart
    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCart(@PathVariable String userId) {
        log.info("Initiated Request for Get Cart with userId : {}", userId);
        CartDto cartDto = cartService.getCartByUser(userId);
        log.info("Completed Request for Get Cart with userId : {}", userId);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }


}
