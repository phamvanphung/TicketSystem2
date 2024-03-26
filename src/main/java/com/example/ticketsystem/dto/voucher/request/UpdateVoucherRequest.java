package com.example.ticketsystem.dto.voucher.request;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateVoucherRequest {

    private String id;
    private String name;
    private String expiredDate ; // dd/MM/yyyy
    private String startDate; // dd/MM/yyyy
    private int discount;
}
