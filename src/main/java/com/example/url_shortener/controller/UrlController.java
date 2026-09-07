package com.example.url_shortener.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.url_shortener.model.Url;
import com.example.url_shortener.service.UrlService;

@RestController
public class UrlController {

    @Value("${app.base-url}")
    private String baseUrl;

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/urls")
    public UrlResponse createUrl(@RequestBody UrlRequest request) {
        String shortCode = urlService.createShortCode(request.getUrl());
        UrlResponse response = new UrlResponse();
        response.setShortCode(shortCode);
        response.setOriginalUrl(request.getUrl());
        response.setShortUrl(baseUrl + "/" + shortCode);
        return response;
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        Url originalUrl = urlService.getOriginalUrl(shortCode);
        if (originalUrl == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.status(302).header("Location", originalUrl.toString()).build();
    }

}