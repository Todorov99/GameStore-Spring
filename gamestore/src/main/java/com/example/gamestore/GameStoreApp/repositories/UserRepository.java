package com.example.gamestore.GameStoreApp.repositories;

import com.example.gamestore.GameStoreApp.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findAllByEmail(String email);

}
