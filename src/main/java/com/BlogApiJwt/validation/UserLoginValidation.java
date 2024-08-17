package com.BlogApiJwt.validation;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginValidation {

    @NotNull(message = "email is required")
    @NotEmpty(message = "email not be empty")
    @Email(message = "email: Invalid email address")
    private String email;

    @NotNull(message = "password is required")
    @NotEmpty(message = "password not be empty")
    private String password;


}
