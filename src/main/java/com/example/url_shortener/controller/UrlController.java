package com.example.url_shortener.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.url_shortener.model.Url;
import com.example.url_shortener.service.UrlService;

import jakarta.validation.Valid;

@RestController
public class UrlController {

    @Value("${app.base-url}")
    private String baseUrl;
    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/urls")
    public ResponseEntity<UrlResponse> createUrl(@RequestBody @Valid UrlRequest request) {
        String shortCode = urlService.createShortCode(request);
        UrlResponse response = new UrlResponse();
        response.setShortCode(shortCode);
        response.setOriginalUrl(request.getUrl());
        response.setShortUrl(baseUrl + "/" + shortCode);
        response.setExpiresAt(request.getExpiresAt());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        Url originalUrl = urlService.getOriginalUrl(shortCode);

        if (originalUrl == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(302).header("Location", originalUrl.getOriginalUrl()).build();
    }

    @GetMapping("/urls/{shortCode}")
    public ResponseEntity<UrlResponse> getUrlDetails(@PathVariable String shortCode) {
        Url urlInfo = urlService.getUrlInfo(shortCode);

        if (urlInfo == null) {
            return ResponseEntity.notFound().build();
        }

        UrlResponse response = new UrlResponse();
        response.setShortCode(urlInfo.getShortCode());
        response.setOriginalUrl(urlInfo.getOriginalUrl());
        response.setShortUrl(baseUrl + "/" + urlInfo.getShortCode());
        response.setExpiresAt(urlInfo.getExpiresAt());

        return ResponseEntity.ok(response);
    }

}