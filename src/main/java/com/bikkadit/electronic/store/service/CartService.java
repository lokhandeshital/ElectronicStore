package com.bikkadit.electronic.store.service;

import com.bikkadit.electronic.store.dtos.AddItemToCartRequest;
import com.bikkadit.electronic.store.dtos.CartDto;

public interface CartService {

    //Add item to cart
    //case1 : cart for user is not available
    //case2 : cart available add the item to cart
    CartDto addItemToCart(String userId, AddItemToCartRequest request);

    //Remove Item from Cart
    void removeItemFromCart(String userId, Integer cartItem);

    //remove All Item From Cart
    void clearCart(String userId);

    CartDto getCartByUser(String userId);


}
