package com.example.url_shortener.controller;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;

public class UrlRequest {
    @NotBlank(message = "URL cannot be blank")
    private String url;

    private LocalDateTime expiresAt;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }
}