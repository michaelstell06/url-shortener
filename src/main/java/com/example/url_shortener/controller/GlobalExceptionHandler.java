package com.example.url_shortener.controller;

import com.example.url_shortener.exception.UrlExpiredException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}