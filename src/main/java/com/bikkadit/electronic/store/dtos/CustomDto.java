package com.bikkadit.electronic.store.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomDto {

    private String isActive;

    @NotEmpty
    @Size(min = 3,max = 20,message = "Creater Name Should be Min 3 and Max 20 Character")
    private String createdBy;

    @NotEmpty
    @Size(min = 2,max = 15,message = "Modifier Name Should be Min 2 and Max 15 Character ")
    private String modifiedBy;
}
