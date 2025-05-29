package com.ecomm.ecommservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegisterUserRequest {
    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    private String password;

    @NotBlank(message = "First Name cannot be blank")
    private String firstName;
    private String lastName;
}
