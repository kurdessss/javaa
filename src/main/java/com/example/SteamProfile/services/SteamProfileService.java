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
            User user = userRepository.findByUsername(username);
            Location location = new Location();
            location.setLocation(players[0].getLocCountryCode());
            locationRepository.save(location);
            if (user != null) {
                return String.format("User %s already exists", username);
            } else {
                User newUser = new User();
                newUser.setUsername(username);
                newUser.setAvatar(avatar);
                newUser.setLocation(location);
                userRepository.save(newUser);
                return String.format("User %s created", username);
            }
        }

        public String updateUser(String steamId) {
            AllInfo mainResponse = serviceApiKey.doRequestAPI(steamId);
            SteamResponse steamResponse = mainResponse.getSteamResponse();
            ResponseData responseData = steamResponse.getResponse();
            Player[] players = responseData.getPlayers();
            String username = players[0].getPersonaName();
            String avatar = players[0].getAvatarFull();

            User existingUser = userRepository.findByUsername(username);
            if (existingUser != null) {
                existingUser.setAvatar(avatar);
                userRepository.save(existingUser);
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
        public Location updateLocation(long id, String newLocationName) {
            Optional<Location> optionalLocation = locationRepository.findById(id);
            if (optionalLocation.isPresent()) {
                Location location = optionalLocation.get();
                location.setLocation(newLocationName);
                return locationRepository.save(location);
            } else {
                throw new RuntimeException("Местоположение с указанным идентификатором не найдено");
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
                return gameRepository.save(game);
            } else {
                throw new RuntimeException("Игра с указанным идентификатором не найдена");
            }
        }

        public void deleteGame(Long id) {
            gameRepository.deleteById(id);
        }
    }


