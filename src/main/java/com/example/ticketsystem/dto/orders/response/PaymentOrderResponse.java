package com.example.ticketsystem.dto.orders.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class PaymentOrderResponse {
    private String urlVnpay;
}
