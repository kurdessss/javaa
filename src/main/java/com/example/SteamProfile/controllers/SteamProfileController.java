package com.example.SteamProfile.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class SteamProfileController {

    @Value("${steam.api.key}")
    private String steamApiKey;

    private final RestTemplate restTemplate;

    public SteamProfileController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/steam/profile")
    public String getSteamProfile(@RequestParam String steamId) {
        String steamApiUrl = "https://api.steampowered.com/ISteamUser/GetPlayerSummaries/v2/?key=" + steamApiKey + "&steamids=" + steamId;

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(steamApiUrl, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody(); // Возвращаем тело ответа
        } else {
            return "Failed to fetch Steam profile data";
        }
    }
}
