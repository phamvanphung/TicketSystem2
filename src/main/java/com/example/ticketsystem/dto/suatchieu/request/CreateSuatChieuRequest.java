package com.example.ticketsystem.dto.suatchieu.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalTime;

@Getter
@Setter
@ToString
public class CreateSuatChieuRequest {
    private String name;
    private String gioBatDau; // HH:mm
}
