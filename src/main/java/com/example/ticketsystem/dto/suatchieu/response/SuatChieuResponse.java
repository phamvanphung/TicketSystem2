package com.example.ticketsystem.dto.suatchieu.response;


import com.example.ticketsystem.entity.SuatChieu;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@ToString
public class SuatChieuResponse {

    private String id;
    private String name;
    private LocalTime gioBatDau;
    private LocalDateTime createdAt;


    public SuatChieuResponse(SuatChieu suatChieu) {
         this.id = suatChieu.getId().toString();
         this.name = suatChieu.getName();
         this.gioBatDau = suatChieu.getGioBatDau();
         this.createdAt = suatChieu.getCreatedAt();
    }
}
