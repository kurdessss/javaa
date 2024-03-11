package com.example.SteamProfile.models;

public class SteamProfile {
    private String steamId;
    private String personaName;
    private String profileUrl;

    public SteamProfile() {
        // Пустой конструктор для Spring
    }

    // Геттеры и сеттеры для всех полей

    public String getSteamId() {
        return steamId;
    }

    public void setSteamId(String steamId) {
        this.steamId = steamId;
    }

    public String getPersonaName() {
        return personaName;
    }

    public void setPersonaName(String personaName) {
        this.personaName = personaName;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }
}
