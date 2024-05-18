package com.example.SteamProfile.entity;

import com.example.SteamProfile.entity.User;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int playTimeMinutes;

    @ManyToMany(mappedBy = "games", cascade = CascadeType.ALL)
    private List<User> users = new ArrayList<>(); // Инициализация списка

    // Другие поля и методы
}
