package com.BlogApiJwt.config;


import com.BlogApiJwt.exception.CustomException;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.mail.MailSendException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;



@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleSecurityException(Exception exception) {
        ProblemDetail errorDetail = null;

        // TODO send this stack trace to an observability tool
        /// exception.printStackTrace();

        if (exception instanceof BadCredentialsException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
            errorDetail.setProperty("description", "The email or password is incorrect");

            return new ResponseEntity<>(new ApiResponse<Object>(errorDetail.getTitle(),false, errorDetail.getProperties()), HttpStatus.BAD_REQUEST);
        }

        if (exception instanceof AccountStatusException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "The account is locked");
        }

        if (exception instanceof AccessDeniedException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "You are not authorized to access this resource");
        }

        if (exception instanceof SignatureException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "The JWT signature is invalid");
        }

        if (exception instanceof ExpiredJwtException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "The JWT token has expired");
        }

        if (errorDetail == null) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), exception.getMessage());
            errorDetail.setProperty("description", "Unknown internal server error.");
        }
        return new ResponseEntity<>(new ApiResponse<Object>(errorDetail.getTitle(),false, errorDetail.getProperties()), HttpStatus.BAD_REQUEST);
    }




    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());

        return new ResponseEntity<>(new ApiResponse<Object>("validation errors",false, errors), HttpStatus.BAD_REQUEST);
    }





    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handleException(HttpMessageNotReadableException exception, HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<Object>(exception.getMessage(),false), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity handleHttpRequestMethodNotSuppoertedException(HttpRequestMethodNotSupportedException exception, HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<Object>(exception.getMessage(),false), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity handleCustomExceptionException(CustomException exception, HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<Object>(exception.getMessage(),false), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity handleNullPointerExceptionException(NullPointerException exception, HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<Object>(exception.getMessage(),false), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity handleSQLIntegrityConstraintViolationExceptionException(SQLIntegrityConstraintViolationException exception, HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<Object>(exception.getMessage(),false), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(HttpMessageNotWritableException.class)
    public ResponseEntity handleHttpMessageNotWritableException(HttpMessageNotWritableException exception, HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<Object>(exception.getMessage(),false), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(JpaSystemException.class)
    public ResponseEntity handleJpaSystemException(JpaSystemException exception, HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<Object>(exception.getMessage(),false), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity handleNoResourceFoundException(NoResourceFoundException exception, HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<Object>(exception.getMessage(),false), HttpStatus.NOT_FOUND);
    }





//    @ExceptionHandler(EmailAlreadyExistException.class)
//    public ResponseEntity<?> EmailAlreadyExistException(EmailAlreadyExistException ex) {
//        return new ResponseEntity<>(new ApiResponse<>((ex.getMessage()),false), HttpStatus.FORBIDDEN);
//    }


    @ExceptionHandler({NoHandlerFoundException.class})
    public ResponseEntity<ApiResponse> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpServletRequest httpServletRequest) {
        return new ResponseEntity<>(new ApiResponse<>((ex.getMessage()),false), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<ApiResponse> handleUsernameNotFoundException(
            UsernameNotFoundException ex, HttpServletRequest httpServletRequest) {
        return new ResponseEntity<>(new ApiResponse<>((ex.getMessage()),false), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({MailSendException.class})
    public ResponseEntity<ApiResponse> handleMailSendException(
            MailSendException ex, HttpServletRequest httpServletRequest) {
        return new ResponseEntity<>(new ApiResponse<>((ex.getMessage()),false), HttpStatus.UNAUTHORIZED);
    }



    @ExceptionHandler({MessagingException.class})
    public ResponseEntity<ApiResponse> handleMessagingException(
            MessagingException ex, HttpServletRequest httpServletRequest) {
        return new ResponseEntity<>(new ApiResponse<>((ex.getMessage()),false), HttpStatus.UNAUTHORIZED);
    }






}
