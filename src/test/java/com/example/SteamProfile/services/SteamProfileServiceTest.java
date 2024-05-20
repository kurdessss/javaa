package com.example.SteamProfile.services;

import com.example.SteamProfile.models.AllInfo;
import com.example.SteamProfile.models.Player;
import com.example.SteamProfile.models.ResponseData;
import com.example.SteamProfile.models.SteamResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SteamProfileServiceTest {

    @BeforeEach
    void setUp() {
        serviceApiKey = mock(ServiceApiKey.class);
    }

    @Mock
    private ServiceApiKey serviceApiKey;

    @InjectMocks
    private SteamProfileService steamProfileService;

    @Test
    void testSteamProfile() {
        String steamId = "someSteamId";
        AllInfo allInfo = new AllInfo();
        SteamResponse steamResponse = new SteamResponse();
        ResponseData responseData = new ResponseData();
        Player player = new Player();
        player.setPersonaName("TestPlayer");
        responseData.setPlayers(new Player[]{player});
        steamResponse.setResponse(responseData);
        allInfo.setSteamResponse(steamResponse);

        when(serviceApiKey.doRequestAPI(steamId)).thenReturn(allInfo);

        String result = steamProfileService.steamProfile(steamId);

        assertEquals(player.toString(), result);
        verify(serviceApiKey, times(1)).doRequestAPI(steamId);
    }
}
