package com.example.ticketsystem.dto.orders.request;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateOrderRequest {
    private String userId;
    private String voucherId;
    private String filmId;
    private int quality;
}
