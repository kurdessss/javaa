package com.example.SteamProfile.models;

import com.example.SteamProfile.models.GameInfo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameInfoTest {

    @Test
    public void testConstructorAndGetters() {
        // Создаем объект GameInfo с помощью конструктора с аргументами
        GameInfo gameInfo = new GameInfo("Example Game", 120);

        // Проверяем, что значения установлены правильно
        assertEquals("Example Game", gameInfo.getName());
        assertEquals(120, gameInfo.getPlayTimeMinutes());
    }

    @Test
    public void testSetters() {
        // Создаем пустой объект GameInfo
        GameInfo gameInfo = new GameInfo();

        // Устанавливаем значения с помощью сеттеров
        gameInfo.setName("New Game");
        gameInfo.setPlayTimeMinutes(90);

        // Проверяем, что значения установлены правильно
        assertEquals("New Game", gameInfo.getName());
        assertEquals(90, gameInfo.getPlayTimeMinutes());
    }
}
