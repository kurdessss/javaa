package com.example.SteamProfile.services;

import com.example.SteamProfile.models.AllInfo;
import com.example.SteamProfile.models.SteamGames;
import com.example.SteamProfile.models.SteamResponse;
import com.example.SteamProfile.services.ServiceApiKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ServiceApiKeyTest {

    @Mock
    private RestTemplate restTemplate;

    @Value("${steam.api.key}")
    private String apiKey;

    @Value("${steam.api.key}")
    private String steamApiKey; // Добавлено поле steamApiKey

    private ServiceApiKey service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new ServiceApiKey(restTemplate);
    }

    @Test
    public void testDoRequestAPI() {
        String steamId = "12345";
        String steamApiKey = "test_api_key"; // Используем локальную переменную steamApiKey
        String steamApiUrl = "https://api.steampowered.com/ISteamUser/GetPlayerSummaries/v2/?key=" + steamApiKey + "&steamids=" + steamId;
        String recentGamesUrl = "http://api.steampowered.com/IPlayerService/GetRecentlyPlayedGames/v0001/?key=" + steamApiKey + "&steamid=" + steamId + "&format=json";

        SteamResponse steamResponse = new SteamResponse();
        SteamGames steamGames = new SteamGames();
        AllInfo expectedResponse = new AllInfo();
        expectedResponse.setSteamResponse(steamResponse);
        expectedResponse.setSteamGames(steamGames);

        when(restTemplate.getForObject(recentGamesUrl, SteamGames.class)).thenReturn(steamGames);
        when(restTemplate.getForObject(steamApiUrl, SteamResponse.class)).thenReturn(steamResponse);

        AllInfo actualResponse = service.doRequestAPI(steamId);

        assertEquals(expectedResponse, actualResponse);
    }
}
