package com.example.SteamProfile.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SteamGames {
    @JsonProperty("response")
    private GamesData gamesData;
}
