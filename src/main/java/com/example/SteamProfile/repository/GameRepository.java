package com.example.SteamProfile.repository;

import com.example.SteamProfile.entity.Game;
import com.example.SteamProfile.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    @Query("SELECT g FROM Game g WHERE g.playTimeMinutes > :minPlayTime")
    List<Game> findGamesByMinimumPlayTime(int minPlayTime);

    List<Game> findByUsers(User user);
}
