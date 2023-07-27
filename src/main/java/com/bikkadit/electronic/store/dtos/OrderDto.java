package com.bikkadit.electronic.store.dtos;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDto {

    private String orderId;

    private String orderStatus = "PENDING";

    private String paymentStatus = "NOT-PAID";

    private Double orderAmount;

    private String billingAddress;

    private String billingPhone;

    private String billingName;

    private Date orderDate = new Date();

    private Date deliveredDate;

    private UserDto userDto;

    private List<OrderItemDto> orderItems = new ArrayList<>();


}
