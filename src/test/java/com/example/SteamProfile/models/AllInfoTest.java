package com.example.SteamProfile.models;

import com.example.SteamProfile.models.AllInfo;
import com.example.SteamProfile.models.SteamGames;
import com.example.SteamProfile.models.SteamResponse;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class AllInfoTest {

    @Test
    public void testGetterAndSetter() {
        // Создаем фиктивные объекты SteamResponse и SteamGames
        SteamResponse steamResponse = mock(SteamResponse.class);
        SteamGames steamGames = mock(SteamGames.class);

        // Создаем экземпляр класса AllInfo
        AllInfo allInfo = new AllInfo();

        // Устанавливаем фиктивные объекты в свойства allInfo
        allInfo.setSteamResponse(steamResponse);
        allInfo.setSteamGames(steamGames);

        // Проверяем, что полученные объекты соответствуют установленным
        assertEquals(steamResponse, allInfo.getSteamResponse());
        assertEquals(steamGames, allInfo.getSteamGames());
    }
}
