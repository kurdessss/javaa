package com.example.SteamProfile.repository;

import com.example.SteamProfile.entity.PlayTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface recentlyplayRepository extends JpaRepository<PlayTime, Long> {
    // Дополнительные методы могут быть добавлены здесь, если необходимо
}
