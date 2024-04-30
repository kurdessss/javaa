package com.example.SteamProfile.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Games {
    @JsonProperty("name")
    private String name;

    @JsonProperty("playtime_2weeks")
    private String playTime2Weeks;

    @JsonProperty("playtime_forever")
    private String playTimeForever;

    @JsonProperty("img_icon_url")
    private String gameIcon;
}
