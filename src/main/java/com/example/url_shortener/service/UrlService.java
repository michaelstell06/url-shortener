package com.example.url_shortener.service;

import java.net.URI;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.example.url_shortener.model.Url;
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
    public void saveUrl(Url url) {
        urlRepository.save(url);
    }

    public String createShortCode(String url) {

        if (!isValidUrl(url)) {
            throw new IllegalArgumentException("Invalid URL");
        }

        StringBuilder shortCode = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(characters.length());
            shortCode.append(characters.charAt(index));
        }

        Url newUrl = new Url();
        newUrl.setShortCode(shortCode.toString());
        newUrl.setOriginalUrl(url);

        saveUrl(newUrl);

        return shortCode.toString();
    }

    public Url getOriginalUrl(String shortCode) {
        return urlRepository.findByShortCode(shortCode).orElse(null);
    }

    public boolean isValidUrl(String url) {
        try {
            URI uri = URI.create(url);

            return ("http".equalsIgnoreCase(uri.getScheme())
                    || "https".equalsIgnoreCase(uri.getScheme()))
                    && uri.getHost() != null;

        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}