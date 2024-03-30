package com.example.ticketsystem.dto.orders.response;


import com.example.ticketsystem.entity.Film;
import com.example.ticketsystem.entity.Order;
import com.example.ticketsystem.entity.User;
import com.example.ticketsystem.entity.Voucher;
import com.example.ticketsystem.enums.StatusOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class OrderResponse {
    private String id;
    private String userId;
    private Voucher voucher;
    private Film film;
    private int quality;
    private Long total;
    private StatusOrder statusOrder;
    private LocalDateTime createdAt;


    public OrderResponse(Order order) {
        this.id = order.getId().toString();
        this.userId = order.getUser().getId().toString();
        this.voucher = order.getVoucher();
        this.film = order.getFilm();
        this.quality = order.getQuality();
        this.total = order.getTotal();
        this.statusOrder = order.getStatusOrder();
        this.createdAt = order.getCreatedAt();
    }
}
