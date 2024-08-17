package com.BlogApiJwt.controller;


import com.BlogApiJwt.config.ApiResponse;
import com.BlogApiJwt.entity.User;
import com.BlogApiJwt.service.UserService;
import com.BlogApiJwt.validation.ResetPasswordValidation;
import com.BlogApiJwt.validation.UpdateUserValidation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public ResponseEntity getUserData(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return new ResponseEntity<>(new ApiResponse<Object>("User profile fetch successfully", true, currentUser),
                HttpStatus.OK);
    }



    @PostMapping("/update")
    public ResponseEntity editProfile(@RequestBody @Valid UpdateUserValidation updateUserValidation){

        userService.update(updateUserValidation);

        return new ResponseEntity<>(new ApiResponse<Object>("User profile edited successfully", true),
                HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity resetPassword(
            @RequestBody @Valid ResetPasswordValidation resetPasswordValidation
            ){

        userService.resetPassword(resetPasswordValidation);

        return new ResponseEntity<>(new ApiResponse<Object>("Password reset was successful", true),
                HttpStatus.OK);
    }



}
