package com.bikkadit.electronic.store.service;

import com.bikkadit.electronic.store.dtos.CreateOrderRequest;
import com.bikkadit.electronic.store.dtos.OrderDto;
import com.bikkadit.electronic.store.dtos.PageableResponse;

import java.util.List;

public interface OrderService {

    //create order
    OrderDto createOrder(CreateOrderRequest orderDto);


    //Remove order
    void removeOrder(String orderId);

    //get order of user
    List<OrderDto> getOrderOfUser(String userId);

    //get orders
    PageableResponse<OrderDto> getOrders(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);


}
