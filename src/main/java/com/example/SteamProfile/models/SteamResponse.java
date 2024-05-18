package com.example.SteamProfile.models;

import com.example.SteamProfile.models.ResponseData;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class SteamResponse {
    @JsonProperty("response") // Добавим аннотацию @JsonProperty с указанием соответствующего поля в JSON
    private ResponseData response;
}
