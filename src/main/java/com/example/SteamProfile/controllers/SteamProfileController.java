package com.example.SteamProfile.controllers;

import com.example.SteamProfile.entity.Game;
import com.example.SteamProfile.entity.Location;
import com.example.SteamProfile.models.GameInfo;
import com.example.SteamProfile.repository.GameRepository;
import com.example.SteamProfile.repository.UserRepository;
import com.example.SteamProfile.services.SteamProfileService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/steam")
public class SteamProfileController {
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final SteamProfileService steamProfileService;

    @PostMapping("/user")
    public ResponseEntity<String> createUser(@RequestBody String steamId) {
        if (steamId == null || steamId.isEmpty()) {
            return ResponseEntity.badRequest().body("Steam ID cannot be empty");
        }
        return ResponseEntity.ok(steamProfileService.inputUser(steamId));
    }

    @PutMapping("/user/{steamId}")
    public ResponseEntity<String> updateUser(@PathVariable String steamId) {
        return ResponseEntity.ok(steamProfileService.updateUser(steamId));
    }

    @DeleteMapping("/user/{steamId}")
    public ResponseEntity<String> deleteUser(@PathVariable String steamId) {
        return ResponseEntity.ok(steamProfileService.deleteUser(steamId));
    }

    @PostMapping("/game")
    public ResponseEntity<String> createGame(@RequestBody GameInfo gameInfo) {
        if (gameInfo == null || gameInfo.getName() == null || gameInfo.getName().isEmpty()) {
            return ResponseEntity.badRequest().body("Game name cannot be empty");
        }
        Game game = new Game();
        game.setName(gameInfo.getName());
        // Установка других свойств игры
        gameRepository.save(game);
        return ResponseEntity.ok("Game created");
    }

    @PutMapping("/game/{id}")
    public ResponseEntity<String> updateGame(@PathVariable Long id, @RequestBody GameInfo gameInfo) {
        Optional<Game> optionalGame = gameRepository.findById(id);
        if (optionalGame.isPresent()) {
            Game game = optionalGame.get();
            if (gameInfo != null && gameInfo.getName() != null && !gameInfo.getName().isEmpty()) {
                game.setName(gameInfo.getName());
                // Обновление других свойств игры
                gameRepository.save(game);
                return ResponseEntity.ok("Game updated");
            } else {
                return ResponseEntity.badRequest().body("Game name cannot be empty");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

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

    @PostMapping("/location")
    public ResponseEntity<String> createLocation(@RequestBody Location location) {
        if (location == null) {
            return ResponseEntity.badRequest().body("Location cannot be null");
        }
        Location newLocation = steamProfileService.createLocation(String.valueOf(location));
        return ResponseEntity.ok("Location created");
    }

    @GetMapping("/location")
    public ResponseEntity<List<Location>> getAllLocations() {
        List<Location> locations = steamProfileService.getAllLocations();
        return ResponseEntity.ok(locations);
    }

    @GetMapping("/location/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable Long id) {
        Optional<Location> location = steamProfileService.getLocationById(id);
        return location.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/location/{id}")
    public ResponseEntity<String> updateLocation(@PathVariable Long id, @RequestBody Location location) {
        if (location == null) {
            return ResponseEntity.badRequest().body("Location cannot be null");
        }
        Location updatedLocation = steamProfileService.updateLocation(id, String.valueOf(location));
        return ResponseEntity.ok("Location updated");
    }

    @DeleteMapping("/location/{id}")
    public ResponseEntity<String> deleteLocation(@PathVariable Long id) {
        steamProfileService.deleteLocation(id);
        return ResponseEntity.ok("Location deleted");
    }
}
