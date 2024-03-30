package com.example.ticketsystem.service;


import com.example.ticketsystem.dto.common.response.ApiResponse;
import com.example.ticketsystem.dto.common.response.CommonStatusResponse;
import com.example.ticketsystem.dto.film.repsonse.FilmResponse;
import com.example.ticketsystem.dto.film.request.AddNewFilmRequest;
import com.example.ticketsystem.dto.film.request.UpdateFilmRequest;
import com.example.ticketsystem.dto.suatchieu.response.SuatChieuResponse;
import com.example.ticketsystem.entity.Film;
import com.example.ticketsystem.entity.SuatChieu;
import com.example.ticketsystem.enums.ResponseCode;
import com.example.ticketsystem.exceptions.BusinessException;
import com.example.ticketsystem.repository.FilmRepository;
import com.example.ticketsystem.repository.SuatChieuRepository;
import com.example.ticketsystem.service.interfaces.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl  implements FilmService {

    private final FilmRepository filmRepository;
    private final SuatChieuRepository suatChieuRepository;

    @Override
    public ApiResponse<FilmResponse> addNew(AddNewFilmRequest request) {
        try {
            Film film = filmRepository.findByName(request.getName()).orElse(null);
            if(Objects.nonNull(film)) {
                throw new BusinessException(ResponseCode.FILM_EXISTED);
            }



            film = new Film()
                    .setName(request.getName())
                    .setDescription(request.getDescription())
                    .setUrlImage(request.getUrlImage())
                    .setUrlTrailer(request.getUrlTrailer())
                    .setCreatedAt(LocalDateTime.now())
                    .setPrice(request.getPrice());

            if(!request.getSuatChieuList().isEmpty()) {
                for (String id : request.getSuatChieuList()) {
                    SuatChieu suatChieu  = suatChieuRepository.findById(UUID.fromString(id)).orElse(null);
                    if(Objects.nonNull(suatChieu)){
                        film.getSuatChieuList().add(suatChieu);
                    }
                }
            }

            film = filmRepository.save(film);
           return new ApiResponse<FilmResponse>().ok(new FilmResponse(film));

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<List<FilmResponse>> getList() {
        try {
            List<Film> filmList = filmRepository.findAll();
            List<FilmResponse> response = filmList.stream().map(FilmResponse::new).collect(Collectors.toList());
            return new ApiResponse<List<FilmResponse>>().ok(response);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<FilmResponse> getDetail(String id) {
        try {
            Film film = filmRepository.findById(UUID.fromString(id)).orElseThrow(
                    () -> new BusinessException(ResponseCode.FILM_NOT_FOUND)
            );

            return new ApiResponse<FilmResponse>().ok(new FilmResponse(film));

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<FilmResponse> update(UpdateFilmRequest request) {
        try {
            Film film = filmRepository.findById(UUID.fromString(request.getId())).orElseThrow(
                    () -> new BusinessException(ResponseCode.FILM_NOT_FOUND)
            );


            if(StringUtils.hasText(request.getName())) {
                film.setName(request.getName());
            }

            if(StringUtils.hasText(request.getDescription())) {
                film.setDescription(request.getDescription());
            }

            if(StringUtils.hasText(request.getUrlImage())){
                film.setUrlImage(request.getUrlImage());
            }

            if(StringUtils.hasText(request.getUrlTrailer())){
                film.setUrlTrailer(request.getUrlTrailer());
            }

            if(Objects.nonNull(request.getPrice()) && request.getPrice() > 0) {
                film.setPrice(request.getPrice());
            }
            List<SuatChieu> lis = new ArrayList<>();

            if(Objects.nonNull(request.getSuatChieuList()) && !request.getSuatChieuList().isEmpty()) {
                for (String id : request.getSuatChieuList()) {
                    SuatChieu suatChieu = suatChieuRepository.findById(UUID.fromString(id)).orElse(null);
                    if (Objects.nonNull(suatChieu)) {
                        lis.add(suatChieu);
                    }
                }
                if(!lis.isEmpty()) {
                    film.setSuatChieuList(lis);
                }
            }


            filmRepository.save(film);
            return new ApiResponse<FilmResponse>().ok(new FilmResponse(film));

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<CommonStatusResponse> delete(String id) {
        try {
            Film film = filmRepository.findById(UUID.fromString(id)).orElseThrow(
                    () -> new BusinessException(ResponseCode.FILM_NOT_FOUND)
            );

            filmRepository.delete(film);
            return new ApiResponse<CommonStatusResponse>().ok(new CommonStatusResponse(true));
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }
}
