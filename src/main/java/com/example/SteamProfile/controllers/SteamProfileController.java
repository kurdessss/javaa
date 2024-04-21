package com.example.SteamProfile.controllers;

import com.example.SteamProfile.Service.UserService;
import com.example.SteamProfile.models.GameInfo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SteamProfileController {

    private final UserService userService;
    private final RestTemplate restTemplate;

    @Value("${steam.api.key}")
    private String steamApiKey;

    @Autowired
    public SteamProfileController(UserService userService, RestTemplate restTemplate) {
        this.userService = userService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/")
    public String homePage() {
        return "index";
    }

    @PostMapping("/steam/profile")
    public String getSteamProfile(@RequestParam String steamId, Model model) {
        String steamApiUrl = "https://api.steampowered.com/ISteamUser/GetPlayerSummaries/v2/?key=" + steamApiKey + "&steamids=" + steamId;
        String recentGamesUrl = "http://api.steampowered.com/IPlayerService/GetRecentlyPlayedGames/v0001/?key=" + steamApiKey + "&steamid=" + steamId + "&format=json";

        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(steamApiUrl, String.class);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                JSONObject jsonResponse = new JSONObject(responseEntity.getBody());
                JSONArray players = jsonResponse.getJSONObject("response").getJSONArray("players");
                JSONObject player = players.getJSONObject(0);

                // Получение информации о профиле Steam и передача в модель
                model.addAttribute("personaname", player.getString("personaname"));
                model.addAttribute("avatarfull", player.getString("avatarfull"));

                // Получение списка недавно сыгранных игр
                ResponseEntity<String> recentGamesResponseEntity = restTemplate.getForEntity(recentGamesUrl, String.class);
                if (recentGamesResponseEntity.getStatusCode().is2xxSuccessful()) {
                    JSONObject recentGamesJson = new JSONObject(recentGamesResponseEntity.getBody());
                    JSONArray gamesArray = recentGamesJson.getJSONObject("response").getJSONArray("games");

                    List<GameInfo> recentGames = new ArrayList<>();
                    for (int i = 0; i < gamesArray.length(); i++) {
                        JSONObject game = gamesArray.getJSONObject(i);
                        String gameName = game.getString("name");
                        int playtime2Weeks = game.getInt("playtime_2weeks");
                        recentGames.add(new GameInfo(gameName, playtime2Weeks));
                    }
                    model.addAttribute("recentGames", recentGames);
                } else {
                    model.addAttribute("error", "Failed to retrieve recently played games.");
                    return "error";
                }
            } else {
                model.addAttribute("error", "Failed to retrieve Steam profile information.");
                return "error";
            }
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred while processing the request.");
            return "error";
        }
        return "profile";
    }
}
