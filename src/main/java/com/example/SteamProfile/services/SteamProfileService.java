package com.example.SteamProfile.services;

import com.example.SteamProfile.entity.Game;
import com.example.SteamProfile.entity.Location;
import com.example.SteamProfile.entity.User;
import com.example.SteamProfile.models.AllInfo;
import com.example.SteamProfile.models.Player;
import com.example.SteamProfile.models.ResponseData;
import com.example.SteamProfile.models.SteamResponse;
import com.example.SteamProfile.repository.GameRepository;
import com.example.SteamProfile.repository.LocationRepository;
import com.example.SteamProfile.repository.UserRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Data
public class SteamProfileService {
    private UserRepository userRepository;
    private ServiceApiKey serviceApiKey;
    private LocationRepository locationRepository;
    private GameRepository gameRepository;

    @Autowired
    public void setLocationRepository(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setGameRepository(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public SteamProfileService(ServiceApiKey serviceApiKey) {
        this.serviceApiKey = serviceApiKey;
    }

    public String steamProfile(String steamId) {
        log.info("Post new profile");
        AllInfo playerInfo = serviceApiKey.doRequestAPI(steamId);
        Player player = playerInfo.getSteamResponse().getResponse().getPlayers()[0];
        return player.toString();
    }

    public String inputUser(String steamId) {
        AllInfo mainResponse = serviceApiKey.doRequestAPI(steamId);
        SteamResponse steamResponse = mainResponse.getSteamResponse();
        ResponseData responseData = steamResponse.getResponse();
        Player[] players = responseData.getPlayers();
        String username = players[0].getPersonaName();
        String avatar = players[0].getAvatarFull();

        // Find existing user by username
        User user = userRepository.findByUsername(username);

        // Retrieve or create location
        String countryCode = players[0].getLocCountryCode();
        Location location = locationRepository.findByLocation(countryCode);
        if (location == null) {
            location = new Location();
            location.setLocation(countryCode);
            locationRepository.save(location);
        }

        // Check if user already exists
        if (user != null) {
            return String.format("User %s already exists", username);
        } else {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setAvatarUrl(avatar);
            newUser.setLocation(location);
            userRepository.save(newUser);
            return String.format("User %s created", username);
        }
    }

    public String updateUser(String steamId, String newAvatar) {
        AllInfo mainResponse = serviceApiKey.doRequestAPI(steamId);
        SteamResponse steamResponse = mainResponse.getSteamResponse();
        ResponseData responseData = steamResponse.getResponse();
        Player[] players = responseData.getPlayers();
        String username = players[0].getPersonaName();
        User existingUser = userRepository.findByUsername(username);
        if (existingUser != null) {
            existingUser.setAvatarUrl(newAvatar);
            existingUser.setUsername(username);
            //existingUser.setCreateDate(statedate); // Обновление других полей пользователя, если необходимо
            System.out.println(existingUser.getAvatarUrl());
            userRepository.save(existingUser); // Сохранение обновленного пользователя в базе данных
            return String.format("User %s updated", username);
        } else {
            return String.format("User %s does not exist", username);
        }
    }

    public String deleteUser(String steamId) {
        AllInfo mainResponse = serviceApiKey.doRequestAPI(steamId);
        SteamResponse steamResponse = mainResponse.getSteamResponse();
        ResponseData responseData = steamResponse.getResponse();
        Player[] players = responseData.getPlayers();
        String username = players[0].getPersonaName();
        User existingUser = userRepository.findByUsername(username);
        if (existingUser != null) {
            userRepository.delete(existingUser);
            return String.format("User %s deleted", username);
        } else {
            return String.format("User %s does not exist", username);
        }
    }

    // Создание нового местоположения
    public Location createLocation(String locationName) {
        Location newLocation = new Location();
        newLocation.setLocation(locationName);
        return locationRepository.save(newLocation);
    }

    // Получение всех местоположений
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    // Получение местоположения по идентификатору
    public Optional<Location> getLocationById(long id) {
        return locationRepository.findById(id);
    }

    // Обновление местоположения
    public Location updateLocation(Long id, String newLocation) {
        Optional<Location> optionalLocation = locationRepository.findById(id);
        if (optionalLocation.isPresent()) {
            Location location = optionalLocation.get();
            location.setLocation(newLocation);  // assuming setLocation is a setter for the location field
            return locationRepository.save(location);
        } else {
            throw new RuntimeException("Location with the specified ID not found");
        }
    }

    // Удаление местоположения
    public void deleteLocation(long id) {
        locationRepository.deleteById(id);
    }
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public Optional<Game> getGameById(Long id) {
        return gameRepository.findById(id);
    }

    public Game createGame(String name, int playTimeMinutes, List<Location> locations) {
        Game game = new Game();
        game.setName(name);
        game.setPlayTimeMinutes(playTimeMinutes);
        return gameRepository.save(game);
    }

    public Game updateGame(Long id, String newName, int newPlayTimeMinutes, List<Location> newLocations) {
        Optional<Game> optionalGame = gameRepository.findById(id);
        if (optionalGame.isPresent()) {
            Game game = optionalGame.get();
            game.setName(newName);
            game.setPlayTimeMinutes(newPlayTimeMinutes);
            Game updatedGame = gameRepository.save(game); // Сохраняем обновленную игру
            return updatedGame;
        } else {
            throw new RuntimeException("Игра с указанным идентификатором не найдена");
        }
    }

    public void deleteGame(Long gameId) {
        // Проверяем, существует ли игра с указанным gameId
        if (gameRepository.existsById(gameId)) {
            // Если игра существует, удаляем ее
            gameRepository.deleteById(gameId);
        } else {
            // Если игра не существует, выбрасываем исключение
            throw new RuntimeException("Game not found");
        }
    }

    public List<User> bulkCreateUsers(List<User> users) {
        return users.stream()
                .map(userRepository::save)
                .collect(Collectors.toList());
    }
}


