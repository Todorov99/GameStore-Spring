package com.example.gamestore.GameStoreApp.controllers;

import com.example.gamestore.GameStoreApp.domain.dtos.*;
import com.example.gamestore.GameStoreApp.services.GameService;
import com.example.gamestore.GameStoreApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@Controller
public class GameStoreController implements CommandLineRunner {

    private final UserService userService;
    private final GameService gameService;

    @Autowired
    public GameStoreController(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        while (true){
            String[] params = scanner.nextLine().split("\\|");

            switch (params[0]){
                case "RegisterUser":
                    UserRegisterDto userRegisterDto = new UserRegisterDto(params[1], params[2],
                            params[3], params[4]);
                    System.out.println(this.userService.registerUser(userRegisterDto));
                    break;

                case "LoginUser":

                    UserLoginDto userLoginDto = new UserLoginDto(params[1], params[2]);
                    System.out.println(this.userService.loginUser(userLoginDto));
                    this.gameService.setLoggedInUser(userLoginDto.getEmail());
                    break;

                case "Logout":
                    System.out.println(this.userService.logout());
                    this.gameService.logOutUser();
                    break;

                case "AddGame":
                    GameAddDto gameAddDto = new GameAddDto(params[1], params[4], params[5],
                            Double.parseDouble(params[3]), new BigDecimal(params[2]), params[6],
                       LocalDate.parse(params[7], DateTimeFormatter.ofPattern("dd-MM-yyyy")));

                    System.out.println(this.gameService.addGame(gameAddDto));

                    break;

                case "EditGame":
                    EditGameDto editGameDto = new EditGameDto(Integer.parseInt(params[1]),
                            new BigDecimal(params[2]), Double.parseDouble(params[3]));

                    System.out.println(this.gameService.editGame(editGameDto));
                    break;

                case "DeleteGame":
                    DeleteGameDto deleteGameDto = new DeleteGameDto(Integer.parseInt(params[1]));

                    System.out.println(this.gameService.deleteGame(deleteGameDto));
                    break;

                case "AllGames":
                    this.gameService.viewAllGames().forEach(System.out::println);
                    break;
                case "DetailGame":
                    DetailsGameDto detailsGameDto = new DetailsGameDto(params[1]);
                    System.out.println(this.gameService.detailsGame(detailsGameDto));
                    break;
                case "OwnedGames":
                    this.gameService.ownedGames().forEach(System.out::println);
                    break;
            }
        }
    }
}
