package com.example.SteamProfile.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    @JsonIgnore // Эта аннотация исключает поле users из сериализации
    private List<User> users = new ArrayList<>();

    public Location(String location) {
        this.location = location;
    }
}





