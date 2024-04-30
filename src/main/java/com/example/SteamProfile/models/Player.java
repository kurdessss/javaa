package com.example.SteamProfile.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Player {
    @JsonProperty("steamid")
    private String steamId;

    @JsonProperty("communityvisibilitystate")
    private int communityVisibilityState;

    @JsonProperty("profilestate")
    private int profileState;

    @JsonProperty("personaname")
    private String personaName;

    @JsonProperty("profileurl")
    private String profileUrl;

    private String avatar;

    @JsonProperty("avatarmedium")
    private String avatarMedium;

    @JsonProperty("avatarfull")
    private String avatarFull;

    @JsonProperty("avatarhash")
    private String avatarHash;

    @JsonProperty("lastlogoff")
    private long lastLogOff;

    @JsonProperty("personastate")
    private int personaState;

    @JsonProperty("primaryclanid")
    private String primaryClanId;

    @JsonProperty("timecreated")
    private long timeCreated;

    @JsonProperty("personastateflags")
    private int personaStateFlags;

    @JsonProperty("loccountrycode")
    private String locCountryCode;
}
