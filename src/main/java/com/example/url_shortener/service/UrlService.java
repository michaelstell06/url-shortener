package com.example.url_shortener.service;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.example.url_shortener.controller.UrlRequest;
import com.example.url_shortener.exception.UrlExpiredException;
import com.example.url_shortener.model.Url;
import com.example.url_shortener.repository.UrlRepository;

@Service
public class UrlService {

    private final String characters =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private final Random random = new Random();

    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public void saveUrl(Url url) {
        urlRepository.save(url);
    }

    public String createShortCode(UrlRequest request) {

        if (!isValidUrl(request.getUrl())) {
            throw new IllegalArgumentException("URL must use http or https and contain a valid host");
        }

        if (!isValidTime(request.getExpiresAt())) {
            throw new IllegalArgumentException("Expiration time must be in the future");
        }

        StringBuilder shortCode = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(characters.length());
            shortCode.append(characters.charAt(index));
        }

        //Checks wether the generated short code already exists in the database, if it does, it generates a new one
        if (urlRepository.findByShortCode(shortCode.toString()).isPresent()) {
            return createShortCode(request);
        }

        Url newUrl = new Url();
        newUrl.setShortCode(shortCode.toString());
        newUrl.setOriginalUrl(request.getUrl());
        newUrl.setExpiresAt(request.getExpiresAt());

        saveUrl(newUrl);

        return shortCode.toString();
    }

    public Url getOriginalUrl(String shortCode) {
        Url url = urlRepository.findByShortCode(shortCode).orElse(null);

        if (url == null) {
            return null;
        }

        if (url.getExpiresAt() != null && url.getExpiresAt().isBefore(java.time.LocalDateTime.now())) {
            urlRepository.delete(url);
            throw new UrlExpiredException("This short URL has expired");
        }

        return url;
    }

    public boolean isValidUrl(String url) {
        try {
            if (url == null || url.isEmpty()) {
                return false;
            }

            URI uri = URI.create(url);

            return ("http".equalsIgnoreCase(uri.getScheme())
                    || "https".equalsIgnoreCase(uri.getScheme()))
                    && uri.getHost() != null;

        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean isValidTime(LocalDateTime expiresAt) {
        return expiresAt == null
                || !expiresAt.isBefore(java.time.LocalDateTime.now());
    }
}