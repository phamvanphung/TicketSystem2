package com.example.ticketsystem.dto.voucher.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GiveVoucherRequest {
    private String id;
    private String userId;
}
