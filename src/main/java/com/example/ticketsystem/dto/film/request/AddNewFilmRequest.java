package com.example.ticketsystem.dto.film.request;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class AddNewFilmRequest {
    private String name;
    private String description;
    private String urlImage;
    private String urlTrailer;
    private int price;
    private List<String> suatChieuList; // id
}
