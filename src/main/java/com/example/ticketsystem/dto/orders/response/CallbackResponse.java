package com.example.ticketsystem.dto.orders.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CallbackResponse {
    private String RspCode;
    private String Message;
}
