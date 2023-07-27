package com.bikkadit.electronic.store.dtos;

import com.bikkadit.electronic.store.model.Order;
import com.bikkadit.electronic.store.model.Product;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrderItemDto {

    private Integer orderItemId;

    private Integer quantity;

    private Integer totalPrize;

    private ProductDto productDto;


}
