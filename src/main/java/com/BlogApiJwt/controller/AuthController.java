package com.BlogApiJwt.controller;


import com.BlogApiJwt.config.ApiResponse;
import com.BlogApiJwt.entity.User;
import com.BlogApiJwt.service.JwtService;
import com.BlogApiJwt.service.UserService;
import com.BlogApiJwt.validation.UserLoginValidation;
import com.BlogApiJwt.validation.UserValidation;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController()
public class AuthController {

    private UserService userService;
    private JwtService jwtService;

    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }


    @PostMapping("/api/auth/register")
    public ResponseEntity registerUser(@RequestBody @Valid UserValidation userValidation){

        User user = userService.findByEmail(userValidation.getEmail());
        if(user != null){
            return new ResponseEntity<>(new ApiResponse<Object>("Email already registered", true), HttpStatus.OK);
        }
        //// Save ////
        User savedUser = userService.save(userValidation);
        return new ResponseEntity<>(new ApiResponse<Object>("User registered successfully",true, savedUser), HttpStatus.OK);
    }



    @PostMapping("/api/auth/login")
    public ResponseEntity loginUser(@RequestBody @Valid UserLoginValidation userLoginValidation){

        User user = userService.findByEmail(userLoginValidation.getEmail());
        if(user == null){
            return new ResponseEntity<>(new ApiResponse<Object>("Email not found", true), HttpStatus.OK);
        }

        User authenticatedUser = userService.loginUser(userLoginValidation);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        return new ResponseEntity<>(new ApiResponse<Object>("User login successfully", true, authenticatedUser, jwtToken, jwtService.getExpirationTime()), HttpStatus.OK);
    }


    @GetMapping("/api/access-denied")
    public ResponseEntity accessDenied(){
        return new ResponseEntity(new ApiResponse<Object>("Access denied", false), HttpStatus.FORBIDDEN);
    }


}
