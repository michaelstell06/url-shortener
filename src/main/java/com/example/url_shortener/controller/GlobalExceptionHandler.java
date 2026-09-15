package com.example.url_shortener.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.url_shortener.exception.UrlExpiredException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException exception) {

        ErrorResponse error = new ErrorResponse();
        error.setError("Invalid URL");
        error.setMessage(exception.getMessage());

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(UrlExpiredException.class)
    public ResponseEntity<ErrorResponse> handleUrlExpiredException(UrlExpiredException exception) {

        ErrorResponse error = new ErrorResponse();
        error.setError("URL Expired");
        error.setMessage(exception.getMessage());

        return ResponseEntity.status(410).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {

        ErrorResponse error = new ErrorResponse();
        error.setError("Invalid Request Body");
        error.setMessage("The request body contains invalid or unreadable data");

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        ErrorResponse error = new ErrorResponse();
        error.setError("Validation Error");
        error.setMessage(exception.getBindingResult().getFieldError().getDefaultMessage());

        return ResponseEntity.badRequest().body(error);
    }
}