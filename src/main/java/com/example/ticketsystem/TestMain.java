package com.example.ticketsystem;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestMain {

    public static void main(String[] args) {
        String day = "12/03/2024";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime date = LocalDate.parse(day,dateTimeFormatter).atStartOfDay();
        System.out.printf("In ngay parse:" + date);
    }
}
