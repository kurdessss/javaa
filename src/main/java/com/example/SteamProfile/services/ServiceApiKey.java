package com.example.SteamProfile.services;

import com.example.SteamProfile.models.AllInfo;
import com.example.SteamProfile.models.SteamGames;
import com.example.SteamProfile.models.SteamResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ServiceApiKey {
    private RestTemplate restTemplate;
    @Value("${steam.api.key}")
    private String apiKey;
    @Value("${steam.api.key}") String steamApiKey;

    public ServiceApiKey(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getApiKey() {
        return apiKey;
    }

    public AllInfo doRequestAPI(String steamId){
        String steamApiUrl = "https://api.steampowered.com/ISteamUser/GetPlayerSummaries/v2/?key=" + steamApiKey + "&steamids=" + steamId;
        String recentGamesUrl = "http://api.steampowered.com/IPlayerService/GetRecentlyPlayedGames/v0001/?key=" + steamApiKey + "&steamid=" + steamId + "&format=json";

        SteamGames steamGames = restTemplate.getForObject(recentGamesUrl, SteamGames.class);
        SteamResponse steamResponse = restTemplate.getForObject(steamApiUrl, SteamResponse.class);
        AllInfo mainResponse = new AllInfo();
        mainResponse.setSteamResponse(steamResponse);
        mainResponse.setSteamGames(steamGames);
        return mainResponse;
    }
}
