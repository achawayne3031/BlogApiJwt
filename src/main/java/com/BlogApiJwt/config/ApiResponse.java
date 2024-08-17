package com.BlogApiJwt.config;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiResponse<T> {

    private String message;

    private boolean status = false;

    private LocalDateTime timeCreated = LocalDateTime.now();

    private T data;

    private String token;

    private long expiresIn;



    public ApiResponse(String message, boolean status, T data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public ApiResponse(String message, boolean status) {
        this.message = message;
        this.status = status;
    }

    public ApiResponse(String message, boolean status, T data, String token) {
        this.message = message;
        this.status = status;
        this.data = data;
        this.token = token;
    }


    public ApiResponse(String message, boolean status, T data, String token, long expiresIn) {
        this.message = message;
        this.status = status;
        this.data = data;
        this.token = token;
        this.expiresIn = expiresIn;
    }
}
