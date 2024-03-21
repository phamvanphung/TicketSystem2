package com.example.ticketsystem.dto.suatchieu.request;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@ToString
public class UpdateSuatChieuRequest {

    private String id;
    private String name;
    private String gioBatDau;
}
