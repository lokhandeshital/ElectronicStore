package com.bikkadit.electronic.store.service.impl;

import com.bikkadit.electronic.store.dtos.AddItemToCartRequest;
import com.bikkadit.electronic.store.dtos.CartDto;
import com.bikkadit.electronic.store.exception.BadApiRequest;
import com.bikkadit.electronic.store.exception.ResourceNotFoundException;
import com.bikkadit.electronic.store.helper.AppConstant;
import com.bikkadit.electronic.store.model.Cart;
import com.bikkadit.electronic.store.model.CartItem;
import com.bikkadit.electronic.store.model.Product;
import com.bikkadit.electronic.store.model.User;
import com.bikkadit.electronic.store.repository.CartItemRepository;
import com.bikkadit.electronic.store.repository.CartRepository;
import com.bikkadit.electronic.store.repository.ProductRepository;
import com.bikkadit.electronic.store.repository.UserRepository;
import com.bikkadit.electronic.store.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {

        String productId = request.getProductId();
        Integer quantity = request.getQuantity();

        if (quantity <= 0) {
            throw new BadApiRequest(AppConstant.QUANTITY_NOT_VALID);
        }

        //fetch the product
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.PRODUCT_NOT_FOUND));

        //fetch the user from Database
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.USER_NOT_FOUND));

        Cart cart = null;
        try {
            cart = cartRepository.findByUser(user).get();
        } catch (NoSuchElementException e) {
            cart = new Cart();
            cart.setCartId(UUID.randomUUID().toString());
        }

        //perform cart operation
        //if cart item already present then update
        AtomicReference<Boolean> updated = new AtomicReference<>(false);
        List<CartItem> items = cart.getItems();
        List<CartItem> updateItems = items.stream().map(item -> {

            if (item.getProduct().getProductId().equals(productId)) {
                //item already present in cart
                item.setQuantity(quantity);
                item.setTotalPrize(quantity * product.getDiscountedPrice());
                updated.set(true);

            }
            return item;
        }).collect(Collectors.toList());

        cart.setItems(updateItems);

        //create Item
        if (!updated.get()) {
            CartItem cartItem = CartItem.builder()
                    .quantity(quantity)
                    .totalPrize(quantity * product.getDiscountedPrice())
                    .cart(cart)
                    .product(product)
                    .build();
            cart.getItems().add(cartItem);
        }
        cart.setUser(user);
        Cart updateCart = cartRepository.save(cart);
        return mapper.map(updateCart, CartDto.class);
    }

    @Override
    public void removeItemFromCart(String userId, Integer cartItem) {

        CartItem cartItem1 = cartItemRepository.findById(cartItem).orElseThrow(() -> new ResourceNotFoundException(AppConstant.CART_NOT_FOUND));
        cartItemRepository.delete(cartItem1);
    }

    @Override
    public void clearCart(String userId) {

        //fetch the user from Database
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.USER_NOT_FOUND));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException(AppConstant.CART_USER_NOT_FOUND));
        cart.getItems().clear();
        cartRepository.save(cart);


    }

    @Override
    public CartDto getCartByUser(String userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.USER_NOT_FOUND));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException(AppConstant.CART_USER_NOT_FOUND));
        return mapper.map(cart, CartDto.class);
    }
}
