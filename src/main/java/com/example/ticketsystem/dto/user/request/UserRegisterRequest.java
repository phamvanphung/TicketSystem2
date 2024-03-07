package com.example.ticketsystem.dto.user.request;


import com.example.ticketsystem.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class UserRegisterRequest {
    private String fullname;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String address;
    private LocalDateTime dob;
    private boolean gender;

}
