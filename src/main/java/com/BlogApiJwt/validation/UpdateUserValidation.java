package com.BlogApiJwt.validation;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.Empty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserValidation {

    @NotNull(message = "full name is NULL")
    @NotEmpty(message = "full name not be empty")
    private String fullName;


    @NotEmpty(message = "email: Email is empty")
    @Email(message = "email: Invalid email address")
    private String email;

}
