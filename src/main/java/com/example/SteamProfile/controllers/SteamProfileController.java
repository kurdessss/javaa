package com.example.SteamProfile.controllers;
import com.example.SteamProfile.entity.Game;
import com.example.SteamProfile.entity.Location;
import com.example.SteamProfile.entity.User;
import com.example.SteamProfile.models.GameInfo;
import com.example.SteamProfile.models.UpdateInfo;
import com.example.SteamProfile.repository.GameRepository;
import com.example.SteamProfile.repository.LocationRepository;
import com.example.SteamProfile.repository.UserRepository;
import com.example.SteamProfile.services.SteamProfileService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.example.SteamProfile.cache.GameCache;

@Slf4j
@AllArgsConstructor
@Data
@RestController
@RequestMapping("/steam")
public class SteamProfileController {
    @Autowired
    private GameCache gameCache;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final SteamProfileService steamProfileService;
    private final LocationRepository locationRepository;

    // Другие методы контроллера

    // Создание нового пользователя
    @PostMapping("/user")
    public ResponseEntity<String> createUser(@RequestParam String steamId) {
        return ResponseEntity.ok(steamProfileService.inputUser(steamId));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    // Обновление информации о пользователе
    @PutMapping("/user/{steamId}")
    public ResponseEntity<String> updateUser(@PathVariable String steamId, @RequestBody UpdateInfo updateInfo) {
        String avatar = updateInfo.getAvatarUrl();
        return ResponseEntity.ok(steamProfileService.updateUser(steamId, avatar));
    }

    // Удаление пользователя
    @DeleteMapping("/user/{steamId}")
    public ResponseEntity<String> deleteUser(@PathVariable String steamId) {

        return ResponseEntity.ok(steamProfileService.deleteUser(steamId));
    }

    @GetMapping("/game/time")
    public ResponseEntity<?> getWithParams(@RequestParam(name = "minPlayTime", required = true) int minPlayTime) {
        List<Game> games = steamProfileService.getAllGames();
        List<Game> filteredGames = games.stream()
                .filter(game -> game.getPlayTimeMinutes() > minPlayTime)
                .collect(Collectors.toList());
        if (!filteredGames.isEmpty()) {
            return ResponseEntity.ok(filteredGames);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // Создание новой игры

    @PostMapping("/game")
    public ResponseEntity<String> createGame(@RequestBody GameInfo gameInfo) {
        Game game = new Game();
        game.setName(gameInfo.getName());
        game.setPlayTimeMinutes(gameInfo.getPlayTimeMinutes());
        gameRepository.save(game);
        if (gameCache != null) {
            gameCache.addToCache(game);
        } else {
// Логирование ошибки или другие действия, если gameCache не инициализирован
        }
        return ResponseEntity.ok("Game created");
    }

    // Обновление информации о игре
    @PutMapping("/game/{id}")
    public ResponseEntity<String> updateGame(@PathVariable Long id, @RequestBody GameInfo gameInfo) {
        Optional<Game> optionalGame = gameRepository.findById(id);
        if (optionalGame.isPresent()) {
            Game game = optionalGame.get();
            game.setName(gameInfo.getName());
            game.setPlayTimeMinutes(gameInfo.getPlayTimeMinutes());
            Game updatedGame = gameRepository.save(game); // Сохраняем обновленную игру
            return ResponseEntity.ok("Game updated");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Удаление игры
    @DeleteMapping("/game/{id}")
    public ResponseEntity<String> deleteGame(@PathVariable Long id) {
        Optional<Game> optionalGame = gameRepository.findById(id);
        if (optionalGame.isPresent()) {
            gameRepository.delete(optionalGame.get());
            return ResponseEntity.ok("Game deleted");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // Создание нового местоположения
    @PostMapping("/location")
    public ResponseEntity<String> createLocation(@RequestParam String location) {
        Location newLocation = steamProfileService.createLocation(location);
        return ResponseEntity.ok("Location created");
    }

    // Получение всех местоположений
    @GetMapping("/location")
    public ResponseEntity<Object> getAllLocations() {
        List<Location> locations = steamProfileService.getAllLocations();
        return ResponseEntity.ok(locations);
    }

    // Получение местоположения по его идентификатору
    @GetMapping("/location/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable Long id) {
        Optional<Location> location = steamProfileService.getLocationById(id);
        return location.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Обновление информации о местоположении
    @PutMapping("/location/{id}")
    public ResponseEntity<String> updateLocation(@PathVariable Long id, @RequestBody Location location) {
        Location updatedLocation = steamProfileService.updateLocation(id, String.valueOf(location));
        return ResponseEntity.ok("Location updated");
    }

    @DeleteMapping("/location/{id}")
    public ResponseEntity<String> deleteLocation(@PathVariable Long id) {
        steamProfileService.deleteLocation(id);
        return ResponseEntity.ok("Location deleted");
    }

    @PostMapping("/users/bulk-create")
    public ResponseEntity<List<User>> bulkCreateUsers(@RequestBody List<User> users) {
        List<User> createdUsers = steamProfileService.bulkCreateUsers(users);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUsers);
    }
}
