package com.bikkadit.electronic.store.dtos;

import com.bikkadit.electronic.store.model.Category;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ProductDto extends CustomDto {

    private String productId;

    private String title;

    private String description;

    private Integer price;

    private Integer discountedPrice;

    private Long quantity;

    private Boolean live;

    private Boolean stock;

    private String productImageName;

    private CategoryDto category;


}
