package com.bikkadit.electronic.store.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto extends CustomDto{

    private String categoryId;

    @Size(min = 4, message = "Title Contain Min 4 Character !!")
    @NotEmpty
    private String title;

    @NotBlank(message = "Description Required !!")
    private String description;

    private String coverImage;


}
