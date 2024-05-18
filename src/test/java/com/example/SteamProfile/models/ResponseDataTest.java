package com.example.SteamProfile.models;

import com.example.SteamProfile.models.ResponseData;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ResponseDataTest {

    @Test
    public void testJsonPropertyPlayers() throws NoSuchFieldException {
        // Проверяем, что поле players аннотировано с помощью @JsonProperty
        Field field = ResponseData.class.getDeclaredField("players");
        assertNotNull(field.getAnnotation(JsonProperty.class), "Field 'players' should be annotated with @JsonProperty");

        // Проверяем, что поле players правильно аннотировано как "players"
        assertJsonPropertyName("players", field);
    }

    private void assertJsonPropertyName(String expectedName, java.lang.reflect.Field field) {
        JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
        assertNotNull(jsonProperty, "JsonProperty annotation should not be null");
        assertEquals(expectedName, jsonProperty.value());
    }
}
