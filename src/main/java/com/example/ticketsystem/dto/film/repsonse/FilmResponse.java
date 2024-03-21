package com.example.ticketsystem.dto.film.repsonse;


import com.example.ticketsystem.entity.Film;
import com.example.ticketsystem.entity.SuatChieu;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class FilmResponse {


    private String id;
    private String name;
    private String description;
    private String urlImage;
    private String urlTrailer;
    private LocalDateTime createdAt;
    private int price;
    private List<String> suatChieuList;


    public FilmResponse(Film film) {

        this.id = film.getId().toString();
        this.name = film.getName();
        this.description = film.getDescription();
        this.urlImage = film.getUrlImage();
        this.urlTrailer = film.getUrlTrailer();
        this.createdAt = film.getCreatedAt();
        this.price = film.getPrice();
        this.suatChieuList = film.getSuatChieuList().stream().map(SuatChieu::getName).collect(Collectors.toList());
    }

}
