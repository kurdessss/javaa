package com.example.SteamProfile.models;

import com.example.SteamProfile.models.Player;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {

    @Test
    public void testJsonPropertyAnnotations() {
        assertJsonPropertyName("steamid", "steamId");
        assertJsonPropertyName("communityvisibilitystate", "communityVisibilityState");
        assertJsonPropertyName("profilestate", "profileState");
        assertJsonPropertyName("personaname", "personaName");
        assertJsonPropertyName("profileurl", "profileUrl");
        assertJsonPropertyName("avatarmedium", "avatarMedium");
        assertJsonPropertyName("avatarfull", "avatarFull");
        assertJsonPropertyName("avatarhash", "avatarHash");
        assertJsonPropertyName("lastlogoff", "lastLogOff");
        assertJsonPropertyName("personastate", "personaState");
        assertJsonPropertyName("primaryclanid", "primaryClanId");
        assertJsonPropertyName("timecreated", "timeCreated");
        assertJsonPropertyName("personastateflags", "personaStateFlags");
        assertJsonPropertyName("loccountrycode", "locCountryCode");
    }

    private void assertJsonPropertyName(String expectedName, String fieldName) {
        try {
            Field field = Player.class.getDeclaredField(fieldName);
            JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
            assertEquals(expectedName, jsonProperty.value());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetterAndSetter() {
        Player player = new Player();
        player.setSteamId("12345");
        assertEquals("12345", player.getSteamId());

        player.setCommunityVisibilityState(1);
        assertEquals(1, player.getCommunityVisibilityState());

        // Так же проведите тесты для остальных полей и их геттеров и сеттеров
    }
}
