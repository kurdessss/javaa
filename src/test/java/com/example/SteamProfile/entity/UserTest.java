package com.example.SteamProfile.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class UserTest {

    @Test
    void testConstructorAndGetters() {
        String username = "TestUser";
        User user = new User();
        user.setUsername(username);

        assertNull(user.getId());
        assertEquals(username, user.getUsername());
        assertNull(user.getAvatarUrl());
        assertEquals(0, user.getCreateDate());
        assertNull(user.getGames());
        assertNull(user.getLocation());
    }

    @Test
    void testSetters() {
        String username = "TestUser";
        User user = new User();
        user.setUsername(username);

        assertEquals(username, user.getUsername());
    }

    @Test
    void testGamesAssociation() {
        User user = new User();
        List<Game> games = new ArrayList<>();

        Game game1 = new Game();
        game1.setName("Game 1");

        Game game2 = new Game();
        game2.setName("Game 2");

        games.add(game1);
        games.add(game2);

        user.setGames(games);

        assertEquals(2, user.getGames().size());
        assertTrue(user.getGames().contains(game1));
        assertTrue(user.getGames().contains(game2));

        for (Game game : user.getGames()) {
            assertTrue(game.getUsers().contains(user));
        }
    }

    @Test
    void testLocationAssociation() {
        User user = new User();
        Location location = new Location();
        location.setLocation("Test Location");

        user.setLocation(location);

        assertEquals(location, user.getLocation());
        assertTrue(location.getUsers().contains(user));
    }
}
