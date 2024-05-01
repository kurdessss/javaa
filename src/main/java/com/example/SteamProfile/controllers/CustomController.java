package com.example.SteamProfile.controllers;

import com.example.SteamProfile.entity.User;
import com.example.SteamProfile.repository.GameRepository;
import com.example.SteamProfile.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/customEndpoint")
    public ResponseEntity<?> getWithParams(@RequestParam("minPlayTime") int minPlayTime) {
        List<User> users = userRepository.findUsersByMinimumPlayTime(minPlayTime);
        if (!users.isEmpty()) {
            return ResponseEntity.ok(users);
        } else {
               return ResponseEntity.notFound().build();
        }
    }
}
