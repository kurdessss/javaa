package com.example.SteamProfile.entity;

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
public class Location {
    @Id
    private String location;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    private List<User> users = new ArrayList<>(); // Инициализация списка

    public Location(String location) {
        this.location = location;
    }
}




