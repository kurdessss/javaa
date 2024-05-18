    package com.example.SteamProfile.entity;
    import static org.junit.jupiter.api.Assertions.*;
    import com.example.SteamProfile.entity.Game;
    import com.example.SteamProfile.repository.GameRepository;
    import jakarta.transaction.Transactional;
    import org.junit.jupiter.api.Test;
    import org.junit.jupiter.api.extension.ExtendWith;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
    import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
    import org.springframework.test.context.junit.jupiter.SpringExtension;

    import java.util.List;
    import java.util.Optional;

    @ExtendWith(SpringExtension.class)
    @DataJpaTest
    @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Включите автоматическую конфигурацию тестовой базы данных
    public class GameTest {

        @Autowired
        private GameRepository gameRepository;

        @Test
        void testSaveGame() {
            Game game = new Game();
            game.setName("Test Game");
            game.setPlayTimeMinutes(100);

            Game savedGame = gameRepository.save(game);

            assertNotNull(savedGame);
            assertNotNull(savedGame.getId());
            assertEquals("Test Game", savedGame.getName());
            assertEquals(100, savedGame.getPlayTimeMinutes());
        }

        @Test
        void testFindById() {
            Game game = new Game();
            game.setName("Test Game");
            game.setPlayTimeMinutes(100);
            Game savedGame = gameRepository.save(game);

            Optional<Game> foundGame = gameRepository.findById(savedGame.getId());

            assertTrue(foundGame.isPresent());
            assertEquals(savedGame.getId(), foundGame.get().getId());
        }

        @Test
        @Transactional
        void testFindAll() {
            Game game1 = new Game();
            game1.setName("Test Game 1");
            game1.setPlayTimeMinutes(100);
            gameRepository.save(game1);

            Game game2 = new Game();
            game2.setName("Test Game 2");
            game2.setPlayTimeMinutes(200);
            gameRepository.save(game2);

            List<Game> games = gameRepository.findAll();

            assertNotNull(games);
            assertEquals(2, games.size());
        }

        @Test
        void testDeleteById() {
            Game game = new Game();
            game.setName("Test Game");
            game.setPlayTimeMinutes(100);
            Game savedGame = gameRepository.save(game);

            gameRepository.deleteById(savedGame.getId());

            Optional<Game> deletedGame = gameRepository.findById(savedGame.getId());
            assertFalse(deletedGame.isPresent());
        }

        @Test
        void testUpdateGame() {
            Game game = new Game();
            game.setName("Test Game");
            game.setPlayTimeMinutes(100);
            Game savedGame = gameRepository.save(game);

            savedGame.setName("Updated Game");
            savedGame.setPlayTimeMinutes(200);
            Game updatedGame = gameRepository.save(savedGame);

            assertEquals("Updated Game", updatedGame.getName());
            assertEquals(200, updatedGame.getPlayTimeMinutes());
        }
    }
