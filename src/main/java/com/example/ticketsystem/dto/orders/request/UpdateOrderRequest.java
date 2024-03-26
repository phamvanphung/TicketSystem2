package com.example.ticketsystem.dto.orders.request;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateOrderRequest {
    private String id;
    private String voucherId;
    private int quality;
}
