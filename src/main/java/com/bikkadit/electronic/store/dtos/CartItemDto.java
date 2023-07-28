package com.bikkadit.electronic.store.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDto {

    private Integer cartItemId;

    private ProductDto productDto;

    private Integer quantity;

    private Integer totalPrize;

    private CartDto cartDto;


}
