package com.example.url_shortener.repository;

import java.util.HashMap;
import java.util.Map;

public class UrlRepository {

    private final Map<String, String> urls = new HashMap<>();

    public void save(String shortCode, String url) {
        // Implementation for saving the short code and URL mapping to a database
        urls.put(shortCode, url);
    }

    public String find(String shortCode) {
        // Implementation for retrieving the original URL based on the short code from a database
        return urls.get(shortCode);
    }
}
