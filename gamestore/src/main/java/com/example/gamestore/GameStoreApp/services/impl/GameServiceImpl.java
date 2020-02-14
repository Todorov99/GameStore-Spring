package com.example.gamestore.GameStoreApp.services.impl;

import com.example.gamestore.GameStoreApp.domain.dtos.DeleteGameDto;
import com.example.gamestore.GameStoreApp.domain.dtos.DetailsGameDto;
import com.example.gamestore.GameStoreApp.domain.dtos.EditGameDto;
import com.example.gamestore.GameStoreApp.domain.dtos.GameAddDto;
import com.example.gamestore.GameStoreApp.domain.entities.Game;
import com.example.gamestore.GameStoreApp.domain.entities.User;
import com.example.gamestore.GameStoreApp.repositories.GameRepository;
import com.example.gamestore.GameStoreApp.repositories.UserRepository;
import com.example.gamestore.GameStoreApp.services.GameService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private String loggedUser;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public String addGame(GameAddDto gameAddDto) {
        StringBuilder sb = new StringBuilder();

        Game game = this.modelMapper.map(gameAddDto, Game.class);

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validation = validatorFactory.getValidator();

        Set<ConstraintViolation<Game>> violation = validation.validate(game);

        if(violation.size() > 0){
            for (ConstraintViolation<Game> gameConstraintViolation : violation) {
                 sb.append(gameConstraintViolation.getMessage()).append(System.lineSeparator());
            }
            return sb.toString();
        }

        User user = this.userRepository.findAllByEmail(this.loggedUser).orElse(null);

        if(!user.getRole().toString().equals("ADMIN")){
            return sb.append(String.format("%s is not admin", user.getFullName())).toString();
        }

        this.gameRepository.saveAndFlush(game);
        Set<Game> games = user.getGames();
        games.add(game);
        user.setGames(games);
        this.userRepository.saveAndFlush(user);
        sb.append(String.format("Added %s", game.getTitle()));

        return sb.toString();
    }

    @Override
    public void setLoggedInUser(String email) {
        this.loggedUser = email;
    }

    @Override
    public void logOutUser() {
        this.loggedUser = "";
    }

    @Override
    public String editGame(EditGameDto editGameDto) {

        StringBuilder sb =  new StringBuilder();

        Game game = this.gameRepository.findById(editGameDto.getId()).orElse(null);

        if (invalidGame(game)) return sb.append("There is no such game.").toString();

        game.setPrice(editGameDto.getPrice());
        game.setSize(editGameDto.getSize());

        sb.append(String.format("Edited %s", game.getTitle()));
        this.gameRepository.editGame(game.getPrice(), game.getSize(), game.getId());

        return sb.toString();
    }

    private boolean invalidGame(Game game) {
        return game == null;
    }

    @Override
    public String deleteGame(DeleteGameDto deleteGameDto) {

        StringBuilder sb = new StringBuilder();

        Game game = this.gameRepository.findById(deleteGameDto.getId()).orElse(null);

        if(invalidGame(game)){
            return sb.append("There is no such game.").toString();
        }

        sb.append(String.format("Deleted %s ", game.getTitle()));
        this.gameRepository.deleteById(deleteGameDto.getId());

        return sb.toString();
    }

    @Override
    public List<String> viewAllGames() {
        return this.gameRepository.getAllAvailableGames()
                .stream()
                .map(game -> String.format("%s %s", game.getTitle(), game.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public String detailsGame(DetailsGameDto detailsGameDto) {
        StringBuilder sb = new StringBuilder();

        Game game = this.gameRepository.findByTitle(detailsGameDto.getTitle()).orElse(null);

        if(invalidGame(game)){
            return sb.append("There is no such game.").toString();
        }

        sb.append(String.format("%s ", game.getDescription()));

        return sb.toString();
    }

    @Override
    public List<String> ownedGames() {

        User user = this.userRepository.findAllByEmail(this.loggedUser).orElse(null);

        List<String> games = new ArrayList<>();

        if(user == null){
         games.add("There is no such user.");
         return games;
        }

        return this.gameRepository.findByUsersId(user.getId())
                .stream()
                .map(game -> String.format("%s", game.getTitle()))
                .collect(Collectors.toList());
    }
}
