package com.example.SteamProfile.models;

import com.example.SteamProfile.entity.Game;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseData {
    private Player[] players;
}
