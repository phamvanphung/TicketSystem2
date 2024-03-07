package com.example.ticketsystem.controler.impl;


import com.example.ticketsystem.controler.IUserController;
import com.example.ticketsystem.dto.common.response.ApiResponse;
import com.example.ticketsystem.dto.user.request.UserRegisterRequest;
import com.example.ticketsystem.dto.user.response.UserResponse;
import com.example.ticketsystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserController implements IUserController {


    @Autowired
    private UserService userService;


    @Override
    public ResponseEntity<ApiResponse<UserResponse>> addUser(UserRegisterRequest request) {

        log.info("Has a request to register user with data : {}", request.toString());
        ApiResponse<UserResponse> response = userService.registerUser(request);
        log.info("Response  to register user with data : {}", request.toString());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
