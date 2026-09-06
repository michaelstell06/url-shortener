package com.example.url_shortener.service;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.example.url_shortener.repository.UrlRepository;

@Service
public class UrlService {

    private final String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final Random random = new Random();

    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public void saveUrl(String shortCode, String url) {
        urlRepository.save(shortCode, url);
    }

    public String createShortCode(String url) {
        StringBuilder shortCode = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(characters.length());
            shortCode.append(characters.charAt(index));
        }
        saveUrl(shortCode.toString(), url);
        return shortCode.toString();
    }
    
    public String getOriginalUrl(String shortCode) {
        return urlRepository.find(shortCode);
    }
}