package com.example.SteamProfile.repository;

import com.example.SteamProfile.entity.Game;
import com.example.SteamProfile.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByGames(Game game);
    public User findByUsername(String username);
}
