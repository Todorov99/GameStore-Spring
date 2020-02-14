package com.example.gamestore.GameStoreApp.repositories;

import com.example.gamestore.GameStoreApp.domain.entities.Game;
import com.example.gamestore.GameStoreApp.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    Optional<Game> findById(Integer id);

    @Modifying
    @Transactional
    @Query("update Game as g set g.price = :price, g.size = :size where g.id = :id")
    void editGame(@Param(value = "price") BigDecimal price, @Param(value = "size") double size,
                  @Param(value = "id") Integer id);

    @Modifying
    @Transactional
    @Query("delete from Game as g where g.id = :id")
    void deleteById(@Param(value = "id") Integer id);

    @Modifying
    @Transactional
    @Query("select g from Game as g")
    List<Game> getAllAvailableGames();

    Optional<Game> findByTitle(String title);

    List<Game> findByUsersId(Integer users_id);
}
