package com.bikkadit.electronic.store.dtos;

import com.bikkadit.electronic.store.validator.ImageNameValid;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto extends CustomDto{

    private String userId;

    @Size(min = 3, max = 20, message = "Username should contain min 3 and max 20 characters")
    private String userName;

    @NotEmpty(message = "Invalid Email Address")
    @Email
    private String email;

    @NotEmpty
    @Size(min = 3, max = 15, message = "Password should be min 3 and max 15 characters")
    private String password;

    @NotEmpty
    @Size(min = 4, max = 10, message = "Invalid Gender")
    private String gender;

    @Size(min = 10, max = 1000, message = "Write Something About Yourself")
    private String about;

    @ImageNameValid
    private String imageName;


}
