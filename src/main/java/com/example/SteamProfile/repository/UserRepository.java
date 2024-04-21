package com.example.SteamProfile.repository;

import com.example.SteamProfile.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Дополнительные методы могут быть добавлены здесь, если необходимо
}
