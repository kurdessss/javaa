package com.example.SteamProfile.models;

import com.example.SteamProfile.models.ResponseData;
import com.example.SteamProfile.models.SteamResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SteamResponseTest {

    @Test
    public void testJsonPropertyResponse() throws NoSuchFieldException {
        // Проверяем, что поле response аннотировано с помощью @JsonProperty
        Field field = SteamResponse.class.getDeclaredField("response");
        assertNotNull(field.getAnnotation(JsonProperty.class), "Field 'response' should be annotated with @JsonProperty");

        // Проверяем, что поле response правильно аннотировано как "response"
        assertJsonPropertyName("response", field);
    }

    private void assertJsonPropertyName(String expectedName, java.lang.reflect.Field field) {
        JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
        assertNotNull(jsonProperty, "JsonProperty annotation should not be null");
        assertEquals(expectedName, jsonProperty.value());
    }
}
