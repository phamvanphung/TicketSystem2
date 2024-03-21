package com.example.ticketsystem.controler.impl;


import com.example.ticketsystem.controler.IFilmController;
import com.example.ticketsystem.dto.common.response.ApiResponse;
import com.example.ticketsystem.dto.common.response.CommonStatusResponse;
import com.example.ticketsystem.dto.film.repsonse.FilmResponse;
import com.example.ticketsystem.dto.film.request.AddNewFilmRequest;
import com.example.ticketsystem.dto.film.request.UpdateFilmRequest;
import com.example.ticketsystem.dto.suatchieu.response.SuatChieuResponse;
import com.example.ticketsystem.service.interfaces.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FilmController implements IFilmController {


    private final FilmService filmService;


    @Override
    public ResponseEntity<ApiResponse<FilmResponse>> addNew(AddNewFilmRequest request) {
        ApiResponse<FilmResponse> response = filmService.addNew(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<List<FilmResponse>>> getList() {
        ApiResponse<List<FilmResponse>> response = filmService.getList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<FilmResponse>> getDetail(String id) {
        ApiResponse<FilmResponse> response = filmService.getDetail(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<FilmResponse>> update(UpdateFilmRequest request) {
        ApiResponse<FilmResponse> response = filmService.update(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<CommonStatusResponse>> delete(String id) {
        ApiResponse<CommonStatusResponse> response = filmService.delete(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
