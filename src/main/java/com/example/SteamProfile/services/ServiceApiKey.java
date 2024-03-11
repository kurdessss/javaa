package com.example.SteamProfile.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServiceApiKey {

    @Value("${steam.api.key}")
    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }
}

