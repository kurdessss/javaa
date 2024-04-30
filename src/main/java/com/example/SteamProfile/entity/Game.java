package com.example.SteamProfile.entity;

import com.example.SteamProfile.entity.Location;
import jakarta.persistence.*;
import lombok.*;
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
    private List<User> users;

    @ManyToOne(optional = false)
    private User user;
}




