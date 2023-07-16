package com.bikkadit.electronic.store.controller;

import com.bikkadit.electronic.store.dtos.AddItemToCartRequest;
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

}
