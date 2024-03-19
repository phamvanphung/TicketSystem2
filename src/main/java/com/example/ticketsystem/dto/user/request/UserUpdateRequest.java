package com.example.ticketsystem.dto.user.request;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserUpdateRequest {
    private String id;
    private String fullname;
    private String phone;
    private String address;
    private String  dob; // format dd/MM/yyyy
    private Boolean gender;
}
