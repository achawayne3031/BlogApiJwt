package com.BlogApiJwt.validation;


import jakarta.validation.constraints.*;
import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserValidation {

    private int id;

    @NotNull(message = "full name is required")
    @NotEmpty(message = "full name not be empty")
    private String fullName;


    @NotNull(message = "email is required")
    @NotEmpty(message = "email not be empty")
    @Email(message = "email: Invalid email address")
    private String email;


    @NotNull(message = "password is required")
    @NotEmpty(message = "password not be empty")
    private String password;


    @NotNull(message = "role is required")
    @NotEmpty(message = "role not be empty")
    private String role;


    /*

    public UserValidation() {
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


     */


}
