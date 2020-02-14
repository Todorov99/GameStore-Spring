package com.example.gamestore.GameStoreApp.services;


import com.example.gamestore.GameStoreApp.domain.dtos.UserLoginDto;
import com.example.gamestore.GameStoreApp.domain.dtos.UserRegisterDto;

public interface UserService {

    String registerUser(UserRegisterDto userRegisterDto);

    String loginUser(UserLoginDto userLoginDto);

    String logout();
}
