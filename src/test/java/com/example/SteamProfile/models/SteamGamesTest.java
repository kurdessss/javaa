package com.example.SteamProfile.models;

import com.example.SteamProfile.models.GamesData;
import com.example.SteamProfile.models.SteamGames;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SteamGamesTest {

    @Test
    public void testJsonPropertyGamesData() throws NoSuchFieldException {
        // Проверяем, что поле gamesData аннотировано с помощью @JsonProperty
        Field field = SteamGames.class.getDeclaredField("gamesData");
        assertNotNull(field.getAnnotation(JsonProperty.class), "Field 'gamesData' should be annotated with @JsonProperty");

        // Проверяем, что поле gamesData правильно аннотировано как "response"
        assertJsonPropertyName("response", field);
    }

    private void assertJsonPropertyName(String expectedName, java.lang.reflect.Field field) {
        JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
        assertNotNull(jsonProperty, "JsonProperty annotation should not be null");
        assertEquals(expectedName, jsonProperty.value());
    }
}
