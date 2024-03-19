package com.example.ticketsystem.dto.user.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserVerifyRequest {
    private String email;
    private String otp;
}
