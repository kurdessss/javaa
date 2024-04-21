package com.example.SteamProfile.Service;

import com.example.SteamProfile.entity.User;

public interface UserService {
    User createUser(User user);
    User getUserById(Long id);
    User updateUser(Long id, User updatedUser);
    void deleteUser(Long id);
}
