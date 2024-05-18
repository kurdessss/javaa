package com.example.SteamProfile.controllers;

import com.example.SteamProfile.cache.GameCache;
import com.example.SteamProfile.controllers.SteamProfileController;
import com.example.SteamProfile.entity.Game;
import com.example.SteamProfile.entity.Location;
import com.example.SteamProfile.entity.User;
import com.example.SteamProfile.models.GameInfo;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class SteamProfileControllerTest {

    @Mock
    private GameCache gameCache;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private SteamProfileService steamProfileService;

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private SteamProfileController steamProfileController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_ShouldReturnOk() {
        String steamId = "testSteamId";
        when(steamProfileService.inputUser(steamId)).thenReturn("User created");

        ResponseEntity<String> response = steamProfileController.createUser(steamId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User created", response.getBody());
    }

    @Test
    void updateUser_ShouldReturnOk() {
        String steamId = "testSteamId";
        when(steamProfileService.updateUser(steamId)).thenReturn("User updated");

        ResponseEntity<String> response = steamProfileController.updateUser(steamId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User updated", response.getBody());
    }

    @Test
    void deleteUser_ShouldReturnOk() {
        String steamId = "testSteamId";
        when(steamProfileService.deleteUser(steamId)).thenReturn("User deleted");

        ResponseEntity<String> response = steamProfileController.deleteUser(steamId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User deleted", response.getBody());
    }

    @Test
    void getWithParams_ShouldReturnFilteredGames() {
        Game game1 = new Game();
        game1.setPlayTimeMinutes(120);
        Game game2 = new Game();
        game2.setPlayTimeMinutes(60);
        when(steamProfileService.getAllGames()).thenReturn(Arrays.asList(game1, game2));

        ResponseEntity<?> response = steamProfileController.getWithParams(90);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<?> body = (List<?>) response.getBody();
        assertNotNull(body);
        assertEquals(1, body.size());
        assertEquals(game1, body.get(0));
    }

    @Test
    void createGame_ShouldReturnOkAndCacheGame() {
        GameInfo gameInfo = new GameInfo("Test Game", 100);
        Game game = new Game();
        when(steamProfileService.createGame(anyString(), anyInt(), anyList())).thenReturn(game);

        ResponseEntity<String> response = steamProfileController.createGame(gameInfo);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Game created", response.getBody());
        verify(gameCache, times(1)).addToCache(game);
    }

    @Test
    void updateGame_ShouldReturnOkIfGameExists() {
        Long gameId = 1L;
        GameInfo gameInfo = new GameInfo("Updated Game", 200);
        Game game = new Game();
        when(steamProfileService.updateGame(eq(gameId), anyString(), anyInt(), anyList())).thenReturn(game);

        ResponseEntity<String> response = steamProfileController.updateGame(gameId, gameInfo);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Game updated", response.getBody());
    }

    @Test
    void updateGame_ShouldReturnNotFoundIfGameDoesNotExist() {
        Long gameId = 1L;
        GameInfo gameInfo = new GameInfo("Updated Game", 200);
        when(steamProfileService.updateGame(eq(gameId), anyString(), anyInt(), anyList())).thenThrow(new RuntimeException());

        ResponseEntity<String> response = steamProfileController.updateGame(gameId, gameInfo);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteGame_ShouldReturnOkIfGameExists() {
        Long gameId = 1L;

        ResponseEntity<String> response = steamProfileController.deleteGame(gameId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Game deleted", response.getBody());
        verify(steamProfileService, times(1)).deleteGame(gameId);
    }

    @Test
    void deleteGame_ShouldReturnNotFoundIfGameDoesNotExist() {
        Long gameId = 1L;
        doThrow(new RuntimeException()).when(steamProfileService).deleteGame(gameId);

        ResponseEntity<String> response = steamProfileController.deleteGame(gameId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createLocation_ShouldReturnOk() {
        String locationName = "Test Location";
        Location location = new Location();
        when(steamProfileService.createLocation(locationName)).thenReturn(location);

        ResponseEntity<String> response = steamProfileController.createLocation(locationName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Location created", response.getBody());
    }

    @Test
    void getAllLocations_ShouldReturnLocations() {
        List<Location> locations = Arrays.asList(new Location(), new Location());
        when(steamProfileService.getAllLocations()).thenReturn(locations);

        ResponseEntity<Object> response = steamProfileController.getAllLocations();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(locations, response.getBody());
    }

    @Test
    void getLocationById_ShouldReturnLocationIfExists() {
        Long locationId = 1L;
        Location location = new Location();
        when(steamProfileService.getLocationById(locationId)).thenReturn(Optional.of(location));

        ResponseEntity<Location> response = steamProfileController.getLocationById(locationId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(location, response.getBody());
    }

    @Test
    void getLocationById_ShouldReturnNotFoundIfLocationDoesNotExist() {
        Long locationId = 1L;
        when(steamProfileService.getLocationById(locationId)).thenReturn(Optional.empty());

        ResponseEntity<Location> response = steamProfileController.getLocationById(locationId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void updateLocation_ShouldReturnOkIfLocationExists() {
        Long locationId = 1L;
        Location location = new Location("Updated Location");
        when(steamProfileService.updateLocation(locationId, "Updated Location")).thenReturn(location);

        ResponseEntity<String> response = steamProfileController.updateLocation(locationId, location);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Location updated", response.getBody());
    }

    @Test
    void deleteLocation_ShouldReturnOk() {
        Long locationId = 1L;

        ResponseEntity<String> response = steamProfileController.deleteLocation(locationId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Location deleted", response.getBody());
        verify(steamProfileService, times(1)).deleteLocation(locationId);
    }

    @Test
    void bulkCreateUsers_ShouldReturnCreatedAndUsers() {
        List<User> users = Arrays.asList(new User(), new User());
        when(steamProfileService.bulkCreateUsers(users)).thenReturn(users);

        ResponseEntity<List<User>> response = steamProfileController.bulkCreateUsers(users);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(users, response.getBody());
    }
}
