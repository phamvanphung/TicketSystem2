package com.example.ticketsystem.service;

import com.example.ticketsystem.dto.common.response.ApiResponse;
import com.example.ticketsystem.dto.user.request.UserRegisterRequest;
import com.example.ticketsystem.dto.user.response.UserResponse;
import com.example.ticketsystem.entity.User;
import com.example.ticketsystem.enums.Role;
import com.example.ticketsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public ApiResponse<UserResponse> registerUser (UserRegisterRequest request){


        User user = userRepository.findUserByEmail(request.getEmail()).orElse(null);

        if(Objects.nonNull(user)){
            return new ApiResponse<UserResponse>()
                    .setCode(1)
                    .setMessage("User existed");
        }
        user = new User();
        user.setEmail(request.getEmail());
        user.setAddress(request.getAddress());
        user.setCreatedAt(LocalDateTime.now());
        user.setFullname(request.getFullname());
        user.setGender(request.isGender());
        user.setRole(Role.USER);
        user.setPassword(user.getPassword());
        user = userRepository.save(user);
        return new ApiResponse<UserResponse>()
                .setCode(0)
                .setMessage("success")
                .setData(new UserResponse(user));
    }

}
