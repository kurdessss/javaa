package com.example.SteamProfile.models;

import com.example.SteamProfile.models.Games;
import com.example.SteamProfile.models.GamesData;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GamesDataTest {

    @Test
    public void testJsonPropertyGames() throws NoSuchFieldException {
        // Проверяем, что поле games правильно аннотировано как "games"
        assertJsonPropertyName("games", GamesData.class.getDeclaredField("games"));
    }

    @Test
    public void testJsonPropertyTotalCountGames() throws NoSuchFieldException {
        // Проверяем, что поле totalCountGames правильно аннотировано как "total_count"
        assertJsonPropertyName("total_count", GamesData.class.getDeclaredField("totalCountGames"));
    }

    private void assertJsonPropertyName(String expectedName, java.lang.reflect.Field field) {
        JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
        assertEquals(expectedName, jsonProperty.value());
    }
}
