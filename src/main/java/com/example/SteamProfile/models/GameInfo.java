package com.example.SteamProfile.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class GameInfo {
    private String name;
    private int playtime2Weeks;
}
