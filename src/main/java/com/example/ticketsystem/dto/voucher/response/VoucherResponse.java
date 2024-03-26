package com.example.ticketsystem.dto.voucher.response;


import com.example.ticketsystem.entity.Voucher;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
public class VoucherResponse {
    private String id;
    private String name;
    private LocalDateTime expired;
    private LocalDateTime createdAt;
    private LocalDateTime start;
    private int discount;
    private String userId;
    private String code;
    private boolean used;
    private String orderId;


    public VoucherResponse(Voucher voucher) {
        this.id = voucher.getId().toString();
        this.name = voucher.getName();
        this.expired = voucher.getExpired();
        this.createdAt = voucher.getCreatedAt();
        this.start = voucher.getStart();
        this.discount = voucher.getDiscount();
        this.userId = Objects.nonNull(voucher.getUser()) ? voucher.getUser().getId().toString() : null;
        this.code = voucher.getCode();
        this.used = voucher.isUsed();
        this.orderId = Objects.nonNull(voucher.getOrder()) ? voucher.getOrder().getId().toString() : null;
    }
}
