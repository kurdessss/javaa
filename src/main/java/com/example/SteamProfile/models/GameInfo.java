package com.example.SteamProfile.models;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameInfo {
    private String name;
    private int playTimeMinutes;
}
