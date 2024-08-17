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
public class ResetPasswordValidation {

    @NotNull(message = "old password is NULL")
    @NotEmpty(message = "old password not be empty")
    private String oldPassword;


    @NotNull(message = "new password is NULL")
    @NotEmpty(message = "new password not be empty")
    private String newPassword;

}
