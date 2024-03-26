package com.example.ticketsystem.dto.voucher.request;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateVoucherRequest {
    private String name;
    private String expiredDate ; // dd/MM/yyyy
    private String startDate; // dd/MM/yyyy
    private int discount;
}
