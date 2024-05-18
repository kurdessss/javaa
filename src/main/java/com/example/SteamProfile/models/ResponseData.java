package com.example.SteamProfile.models;

import com.example.SteamProfile.entity.Game;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseData {
    @JsonProperty("players") // Добавим аннотацию @JsonProperty с указанием соответствующего поля в JSON
    private Player[] players;
}

