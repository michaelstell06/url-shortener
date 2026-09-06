package com.example.url_shortener.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class UrlService {

    private final String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final Random random = new Random();

    private final Map<String, String> urls = new HashMap<>();

    public String createShortCode(String url) {
        StringBuilder shortCode = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(characters.length());
            shortCode.append(characters.charAt(index));
        }
        urls.put(shortCode.toString(), url);
        return shortCode.toString();
    }
    
    public String getOriginalUrl(String shortCode) {
        return urls.get(shortCode);
    }
}