package com.example.gamestore.GameStoreApp.services;

import com.example.gamestore.GameStoreApp.domain.dtos.DeleteGameDto;
import com.example.gamestore.GameStoreApp.domain.dtos.DetailsGameDto;
import com.example.gamestore.GameStoreApp.domain.dtos.EditGameDto;
import com.example.gamestore.GameStoreApp.domain.dtos.GameAddDto;

import java.util.List;

public interface GameService {
    String addGame(GameAddDto gameAddDto);

    void setLoggedInUser(String email);

    void logOutUser();

    String editGame(EditGameDto editGameDto);

    String deleteGame(DeleteGameDto deleteGameDto);

    List<String> viewAllGames();

    String detailsGame(DetailsGameDto detailsGameDto);

    List<String> ownedGames();
}
