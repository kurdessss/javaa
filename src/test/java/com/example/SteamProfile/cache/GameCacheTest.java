package com.example.SteamProfile.cache;

import com.example.SteamProfile.entity.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GameCacheTest {

    @Mock
    private Logger logger;

    @InjectMocks
    private GameCache gameCache;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddToCache() {
        Game game1 = new Game();
        game1.setId(1L);
        Game game2 = new Game();
        game2.setId(2L);

        gameCache.addToCache(game1);
        gameCache.addToCache(game2);

        assertEquals(2, gameCache.getCacheSize());
        verify(logger, times(2)).info(anyString(), Optional.ofNullable(any()));
    }

    @Test
    void testGetFromCache() {
        Game game1 = new Game();
        game1.setId(1L);
        Game game2 = new Game();
        game2.setId(2L);

        gameCache.addToCache(game1);
        gameCache.addToCache(game2);

        Game cachedGame1 = gameCache.getFromCache(game1);
        Game cachedGame2 = gameCache.getFromCache(game2);
        Game cachedGame3 = gameCache.getFromCache(new Game());

        assertEquals(game1, cachedGame1);
        assertEquals(game2, cachedGame2);
        assertEquals(null, cachedGame3);
        verify(logger, times(2)).info(anyString(), Optional.ofNullable(any()));
    }

    @Test
    void testLogCache() {
        List<Game> games = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Game game = new Game();
            game.setId((long) i);
            games.add(game);
        }
        games.forEach(gameCache::addToCache);
        gameCache.logCache();
        verify(logger, times(3)).info(anyString());
    }
}
