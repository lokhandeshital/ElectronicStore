package com.bikkadit.electronic.store.dtos;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto extends CustomDto {

    private String categoryId;

    private String title;

    private String description;

    private String coverImage;


}
