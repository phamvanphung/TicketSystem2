package com.example.ticketsystem.controler.impl;


import com.example.ticketsystem.controler.IUserController;
import com.example.ticketsystem.dto.common.response.ApiResponse;
import com.example.ticketsystem.dto.common.response.CommonStatusResponse;
import com.example.ticketsystem.dto.user.request.UserRegisterRequest;
import com.example.ticketsystem.dto.user.request.UserUpdateRequest;
import com.example.ticketsystem.dto.user.request.UserVerifyRequest;
import com.example.ticketsystem.dto.user.response.UserResponse;
import com.example.ticketsystem.service.interfaces.UserService;
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
        ApiResponse<UserResponse> response = userService.register(request);
        return new ResponseEntity<ApiResponse<UserResponse>>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<UserResponse>> getUser(String id) {
        ApiResponse<UserResponse> response = userService.getInfo(id);
        return new ResponseEntity<ApiResponse<UserResponse>>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(UserUpdateRequest request) {
        ApiResponse<UserResponse> response = userService.update(request);
        return new ResponseEntity<ApiResponse<UserResponse>>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<CommonStatusResponse>> changeStatus(String id) {
        ApiResponse<CommonStatusResponse> response = userService.changeStatus(id);
        return new ResponseEntity<ApiResponse<CommonStatusResponse>>(response, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<ApiResponse<CommonStatusResponse>> verifyRegister(UserVerifyRequest request) {
        ApiResponse<CommonStatusResponse> response = userService.verifyOtpRegister(request);
        return new ResponseEntity<ApiResponse<CommonStatusResponse>>(response, HttpStatus.OK);
    }
}
