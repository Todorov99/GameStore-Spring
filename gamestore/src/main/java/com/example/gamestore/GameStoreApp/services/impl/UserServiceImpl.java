package com.example.gamestore.GameStoreApp.services.impl;

import com.example.gamestore.GameStoreApp.domain.dtos.UserLoginDto;
import com.example.gamestore.GameStoreApp.domain.dtos.UserRegisterDto;
import com.example.gamestore.GameStoreApp.domain.entities.Role;
import com.example.gamestore.GameStoreApp.domain.entities.User;
import com.example.gamestore.GameStoreApp.repositories.UserRepository;
import com.example.gamestore.GameStoreApp.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.*;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private String loggedInUser;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.loggedInUser = "";
    }

    @Override
    public String registerUser(UserRegisterDto userRegisterDto) {

        StringBuilder sb = new StringBuilder();

        User user = this.modelMapper.map(userRegisterDto, User.class);

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        User inDb = this.userRepository.findAllByEmail(user.getEmail()).orElse(null);

        if(inDb != null){
            return sb.append("User is already registered").toString();
        }

        if(violations.size() > 0){
            for (ConstraintViolation<User> violation : violations) {
                sb.append(violation.getMessage()).append(System.lineSeparator());
            }
        }else {

            if(this.userRepository.count() == 0){
                user.setRole(Role.ADMIN);
            }else {
                user.setRole(Role.USER);
            }

            sb.append(String.format("%s was registered.", user.getFullName()));
            this.userRepository.saveAndFlush(user);
        }

        return sb.toString();
    }

    @Override
    public String loginUser(UserLoginDto userLoginDto) {

        StringBuilder sb = new StringBuilder();

        if(!(this.loggedInUser.isEmpty())){
            return sb.append("User is already logged in").toString();
        }

        User user = this.userRepository.findAllByEmail(userLoginDto.getEmail()).orElse(null);

        if(user == null){
            return sb.append("Incorrect email").toString();
        }else {
           if(!(user.getPassword().equals(userLoginDto.getPassword()))){
               return sb.append("Incorrect password!").toString();
           }

           this.loggedInUser = user.getEmail();
               sb.append(String.format("Successfully logged in %s", user.getFullName()));

        }

        return sb.toString();
    }

    @Override
    public String logout() {

        StringBuilder sb = new StringBuilder();

        if(this.loggedInUser.isEmpty()){
            sb.append("Cannot log out. No user logged in.");
        }else {
            User user = this.userRepository.findAllByEmail(this.loggedInUser).orElse(null);

            sb.append(String.format("User %s successfully logged out", user.getFullName()));
            this.loggedInUser = "";
        }

        return sb.toString();
    }
}
