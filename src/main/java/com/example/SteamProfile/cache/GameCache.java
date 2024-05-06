package com.example.SteamProfile.cache;

import com.example.SteamProfile.entity.Game;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.slf4j.Logger; // Добавлен импорт

import java.util.ArrayList;
import java.util.List;

@Component
public class    GameCache {
    private static final int CACHE_SIZE = 5;
    private final List<Game> cache;
    private final Logger logger = LoggerFactory.getLogger(GameCache.class);

    public GameCache() {
        cache = new ArrayList<>();
    }

    public int getCacheSize() {
        return cache.size();
    }

    public void addToCache(Game game) {
        if (cache.size() >= CACHE_SIZE) {
            cache.remove(0);
        }
        cache.add(game);
        logger.info("Added game to cache: {}", game);
    }

    public Game getFromCache(Game game) {
        for (Game cachedGame : cache) {
            if (cachedGame.equals(game)) {
                logger.info("Game found in cache: {}", cachedGame);
                return cachedGame;
            }
        }
        return null;
    }

    public void logCache() {
        logger.info("Current cache contents:");
        for (Game game : cache) {
            logger.info("{}", game);
        }
    }
}
