package com.example.SteamProfile.models;

import com.example.SteamProfile.models.Games;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GamesTest {

    @Test
    public void testJsonPropertyName() throws NoSuchFieldException {
        // Проверяем, что поле name правильно аннотировано как "name"
        assertJsonPropertyName("name", Games.class.getDeclaredField("name"));
    }

    @Test
    public void testJsonPropertyPlayTime2Weeks() throws NoSuchFieldException {
        // Проверяем, что поле playTime2Weeks правильно аннотировано как "playtime_2weeks"
        assertJsonPropertyName("playtime_2weeks", Games.class.getDeclaredField("playTime2Weeks"));
    }

    @Test
    public void testJsonPropertyPlayTimeForever() throws NoSuchFieldException {
        // Проверяем, что поле playTimeForever правильно аннотировано как "playtime_forever"
        assertJsonPropertyName("playtime_forever", Games.class.getDeclaredField("playTimeForever"));
    }

    @Test
    public void testJsonPropertyGameIcon() throws NoSuchFieldException {
        // Проверяем, что поле gameIcon правильно аннотировано как "img_icon_url"
        assertJsonPropertyName("img_icon_url", Games.class.getDeclaredField("gameIcon"));
    }

    private void assertJsonPropertyName(String expectedName, java.lang.reflect.Field field) {
        JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
        assertEquals(expectedName, jsonProperty.value());
    }
}
