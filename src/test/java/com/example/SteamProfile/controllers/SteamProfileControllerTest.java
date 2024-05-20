package com.example.SteamProfile.controllers;

import com.example.SteamProfile.cache.GameCache;
import com.example.SteamProfile.entity.Game;
import com.example.SteamProfile.entity.Location;
import com.example.SteamProfile.entity.User;
import com.example.SteamProfile.models.GameInfo;
import com.example.SteamProfile.models.UpdateInfo;
import com.example.SteamProfile.repository.GameRepository;
import com.example.SteamProfile.repository.LocationRepository;
import com.example.SteamProfile.repository.UserRepository;
import com.example.SteamProfile.services.SteamProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SteamProfileControllerTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SteamProfileService steamProfileService;

    @InjectMocks
    private SteamProfileController steamProfileController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateUser() {
        String steamId = "123456";
        when(steamProfileService.inputUser(steamId)).thenReturn("User created");
        ResponseEntity<String> responseEntity = steamProfileController.createUser(steamId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("User created", responseEntity.getBody());
    }

    @Test
    void testDeleteUser() {
        String steamId = "123456";
        when(steamProfileService.deleteUser(steamId)).thenReturn("User deleted");
        ResponseEntity<String> responseEntity = steamProfileController.deleteUser(steamId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("User deleted", responseEntity.getBody());
    }

    @Test
    void testCreateGame() {
        GameInfo gameInfo = new GameInfo("GameName", 60);
        ResponseEntity<String> responseEntity = steamProfileController.createGame(gameInfo);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Game created", responseEntity.getBody());
    }

    @Test
    void testUpdateGame() {
        Long gameId = 1L;
        GameInfo gameInfo = new GameInfo("UpdatedGameName", 120);
        Game game = new Game();
        game.setName("GameName");
        game.setPlayTimeMinutes(60);
        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        ResponseEntity<String> responseEntity = steamProfileController.updateGame(gameId, gameInfo);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Game updated", responseEntity.getBody());
        assertEquals("UpdatedGameName", game.getName());
        assertEquals(120, game.getPlayTimeMinutes());
        verify(gameRepository, times(1)).save(game);
    }

    @Test
    void testDeleteGame() {
        Long gameId = 1L;
        Game game = new Game();
        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        ResponseEntity<String> responseEntity = steamProfileController.deleteGame(gameId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Game deleted", responseEntity.getBody());
        verify(gameRepository, times(1)).delete(game);
    }

    @Test
    void testCreateLocation() {
        String locationName = "NewLocation";
        Location location = new Location();
        when(steamProfileService.createLocation(locationName)).thenReturn(location);
        ResponseEntity<String> responseEntity = steamProfileController.createLocation(locationName);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Location created", responseEntity.getBody());
    }

    @Test
    void testGetAllLocations() {
        List<Location> locations = new ArrayList<>();
        when(steamProfileService.getAllLocations()).thenReturn(locations);
        ResponseEntity<Object> responseEntity = steamProfileController.getAllLocations();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(locations, responseEntity.getBody());
    }

    @Test
    void testGetLocationById() {
        Long locationId = 1L;
        Location location = new Location();
        when(steamProfileService.getLocationById(locationId)).thenReturn(Optional.of(location));
        ResponseEntity<Location> responseEntity = steamProfileController.getLocationById(locationId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(location, responseEntity.getBody());
    }

    @Test
    void testUpdateLocation() {
        Long locationId = 1L;
        Location location = new Location();
        when(steamProfileService.updateLocation(locationId, String.valueOf(location))).thenReturn(location);
        ResponseEntity<String> responseEntity = steamProfileController.updateLocation(locationId, location);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Location updated", responseEntity.getBody());
    }

    @Test
    void testDeleteLocation() {
        Long locationId = 1L;
        ResponseEntity<String> responseEntity = steamProfileController.deleteLocation(locationId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Location deleted", responseEntity.getBody());
        verify(steamProfileService, times(1)).deleteLocation(locationId);
    }

    @Test
    void testBulkCreateUsers() {
        List<User> users = new ArrayList<>();
        when(steamProfileService.bulkCreateUsers(users)).thenReturn(users);
        ResponseEntity<List<User>> responseEntity = steamProfileController.bulkCreateUsers(users);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(users, responseEntity.getBody());
    }
}
