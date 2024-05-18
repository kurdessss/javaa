package com.example.SteamProfile.entity;

import static org.junit.jupiter.api.Assertions.*;

import com.example.SteamProfile.entity.Location;
import com.example.SteamProfile.entity.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class LocationTest {

    @Test
    void testConstructorAndGetters() {
        String locationName = "Test Location";
        Location location = new Location(locationName);

        assertEquals(locationName, location.getLocation());
        assertNull(location.getUsers());
    }

    @Test
    void testSetters() {
        String locationName = "Test Location";
        Location location = new Location();
        location.setLocation(locationName);

        assertEquals(locationName, location.getLocation());
    }

    @Test
    void testUsersAssociation() {
        Location location = new Location();
        List<User> users = new ArrayList<>();

        User user1 = new User();
        user1.setUsername("User 1");
        user1.setLocation(location);

        User user2 = new User();
        user2.setUsername("User 2");
        user2.setLocation(location);

        users.add(user1);
        users.add(user2);

        location.setUsers(users);

        assertEquals(2, location.getUsers().size());
        assertTrue(location.getUsers().contains(user1));
        assertTrue(location.getUsers().contains(user2));

        for (User user : location.getUsers()) {
            assertEquals(location, user.getLocation());
        }
    }
}
