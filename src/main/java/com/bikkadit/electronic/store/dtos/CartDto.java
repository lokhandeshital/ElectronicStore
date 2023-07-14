package com.bikkadit.electronic.store.dtos;

import com.bikkadit.electronic.store.model.CartItem;
import com.bikkadit.electronic.store.model.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDto {

    private String cartId;

    private User user;

    private List<CartItemDto> items = new ArrayList<>();


}
