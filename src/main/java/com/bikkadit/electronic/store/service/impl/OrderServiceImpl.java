package com.bikkadit.electronic.store.service.impl;

import com.bikkadit.electronic.store.dtos.CreateOrderRequest;
import com.bikkadit.electronic.store.dtos.OrderDto;
import com.bikkadit.electronic.store.dtos.PageableResponse;
import com.bikkadit.electronic.store.exception.BadApiRequest;
import com.bikkadit.electronic.store.exception.ResourceNotFoundException;
import com.bikkadit.electronic.store.helper.AppConstant;
import com.bikkadit.electronic.store.model.*;
import com.bikkadit.electronic.store.repository.CartRepository;
import com.bikkadit.electronic.store.repository.OrderRepository;
import com.bikkadit.electronic.store.repository.UserRepository;
import com.bikkadit.electronic.store.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public OrderDto createOrder(CreateOrderRequest orderDto) {

        String userId = orderDto.getUserId();
        String cartId = orderDto.getCartId();

        //fetch user
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.USER_NOT_FOUND));

        //fetch card
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.CART_NOT_FOUND));

        List<CartItem> cartItems = cart.getItems();

        if (cartItems.size() <= 0) {
            throw new BadApiRequest("Invalid Number of item in cart !!");
        }

        //other check
        Order order = Order.builder()
                .billingName(orderDto.getBillingName())
                .billingPhone(orderDto.getBillingPhone())
                .billingAddress(orderDto.getBillingAddress())
                .orderDate(new Date())
                .deliveredDate(null)
                .paymentStatus(orderDto.getPaymentStatus())
                .orderStatus(orderDto.getOrderStatus())
                .orderId(UUID.randomUUID().toString())
                .user(user)
                .build();

        //orderItems,Amount
        AtomicReference<Integer> orderAmount = new AtomicReference<>(0);
        List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
            //cartItem Converted into OrderItem
            OrderItem orderItem = OrderItem.builder()
                    .quantity(cartItem.getQuantity())
                    .product(cartItem.getProduct())
                    .totalPrize(cartItem.getQuantity() * cartItem.getProduct().getDiscountedPrice())
                    .order(order)
                    .build();

            orderAmount.set(orderAmount.get() + orderItem.getTotalPrize());

            return orderItem;
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);
        order.setOrderAmount(orderAmount.get());

        cart.getItems().clear();
        cartRepository.save(cart);
        Order saveOrder = orderRepository.save(order);
        return mapper.map(saveOrder, OrderDto.class);
    }


    @Override
    public void removeOrder(String orderId) {

    }

    @Override
    public List<OrderDto> getOrderOfUser(String userId) {
        return null;
    }

    @Override
    public PageableResponse<OrderDto> getOrders(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        return null;
    }
}
