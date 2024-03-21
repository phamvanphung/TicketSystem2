package com.example.ticketsystem.service.interfaces;


import com.example.ticketsystem.dto.common.response.ApiResponse;
import com.example.ticketsystem.dto.common.response.CommonStatusResponse;
import com.example.ticketsystem.dto.film.repsonse.FilmResponse;
import com.example.ticketsystem.dto.film.request.AddNewFilmRequest;
import com.example.ticketsystem.dto.film.request.UpdateFilmRequest;

import java.util.List;

public interface FilmService {


    ApiResponse<FilmResponse> addNew(AddNewFilmRequest request);

    ApiResponse<List<FilmResponse>> getList();

    ApiResponse<FilmResponse> getDetail(String id);

    ApiResponse<FilmResponse> update(UpdateFilmRequest request);

    ApiResponse<CommonStatusResponse> delete(String id);
}
