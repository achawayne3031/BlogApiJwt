package com.BlogApiJwt.validation;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordValidation {


    @NotEmpty(message = "email: Email is empty")
    @Email(message = "email: Invalid email address")
    private String email;


}
