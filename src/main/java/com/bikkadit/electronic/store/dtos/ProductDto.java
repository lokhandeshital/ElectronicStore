package com.bikkadit.electronic.store.dtos;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDto {

    private String productId;

    @NotEmpty
    @Size(min = 2,max = 50,message = "Title Contain min 2 and max 50 Character !!")
    private String title;

    @NotEmpty
    private String description;

    @NotEmpty
    private Double price;

    private Double discountedPrice;

    @NotEmpty
    private Long quantity;

    @NotEmpty
    private Boolean live;

    @NotEmpty
    private Boolean stock;


}
