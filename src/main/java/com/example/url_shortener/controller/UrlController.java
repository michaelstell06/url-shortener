package com.example.url_shortener.controller;

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

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/urls")
    public String createUrl(@RequestBody UrlRequest request) {
        return urlService.createShortCode(request.getUrl());
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        Url url = urlService.getOriginalUrl(shortCode);
        if (url == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.status(302).header("Location", url.getOriginalUrl()).build();
    }

}