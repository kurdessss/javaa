package com.example.SteamProfile.models;

import com.example.SteamProfile.entity.Game;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GamesData {
    @JsonProperty("games")
    private Games[] games;
    @JsonProperty("total_count")
    private int totalCountGames;
}
