package com.example.ticketsystem.controler.impl;


import com.example.ticketsystem.controler.ISuatChieuController;
import com.example.ticketsystem.dto.common.response.ApiResponse;
import com.example.ticketsystem.dto.common.response.CommonStatusResponse;
import com.example.ticketsystem.dto.suatchieu.request.CreateSuatChieuRequest;
import com.example.ticketsystem.dto.suatchieu.request.UpdateSuatChieuRequest;
import com.example.ticketsystem.dto.suatchieu.response.SuatChieuResponse;
import com.example.ticketsystem.service.interfaces.SuatChieuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SuatChieuController implements ISuatChieuController {

    private final SuatChieuService suatChieuService;
    @Override
    public ResponseEntity<ApiResponse<SuatChieuResponse>> addNew(CreateSuatChieuRequest request) {
        ApiResponse<SuatChieuResponse> response = suatChieuService.addNew(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<List<SuatChieuResponse>>> getList() {
        ApiResponse<List<SuatChieuResponse>> response = suatChieuService.getList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<SuatChieuResponse>> getDetail(String id) {
        ApiResponse<SuatChieuResponse> response = suatChieuService.getDetail(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<SuatChieuResponse>> update(UpdateSuatChieuRequest request) {
        ApiResponse<SuatChieuResponse> response = suatChieuService.update(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<CommonStatusResponse>> delete(String id) {
        ApiResponse<CommonStatusResponse> response = suatChieuService.delete(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
