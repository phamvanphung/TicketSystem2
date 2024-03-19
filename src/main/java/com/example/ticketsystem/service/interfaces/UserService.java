package com.example.ticketsystem.service.interfaces;

import com.example.ticketsystem.dto.common.response.ApiResponse;
import com.example.ticketsystem.dto.common.response.CommonStatusResponse;
import com.example.ticketsystem.dto.user.request.UserRegisterRequest;
import com.example.ticketsystem.dto.user.request.UserUpdateRequest;
import com.example.ticketsystem.dto.user.request.UserVerifyRequest;
import com.example.ticketsystem.dto.user.response.UserResponse;

public interface UserService {

    ApiResponse<UserResponse> register(UserRegisterRequest request);
    ApiResponse<UserResponse> getInfo(String id);
    ApiResponse<UserResponse> update(UserUpdateRequest request);
    ApiResponse<CommonStatusResponse> changeStatus(String id);

    ApiResponse<CommonStatusResponse> verifyOtpRegister(UserVerifyRequest request);

}
