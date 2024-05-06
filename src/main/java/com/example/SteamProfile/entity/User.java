package com.example.SteamProfile.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String avatar;
    private int stateDate;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_game", // Имя таблицы для хранения связей пользователей и игр
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    private List<Game> games;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

}
