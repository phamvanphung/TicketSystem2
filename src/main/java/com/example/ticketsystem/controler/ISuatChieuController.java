package com.example.ticketsystem.controler;


import com.example.ticketsystem.dto.common.response.ApiResponse;
import com.example.ticketsystem.dto.common.response.CommonStatusResponse;
import com.example.ticketsystem.dto.suatchieu.request.CreateSuatChieuRequest;
import com.example.ticketsystem.dto.suatchieu.request.UpdateSuatChieuRequest;
import com.example.ticketsystem.dto.suatchieu.response.SuatChieuResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/suatchieu")
public interface ISuatChieuController {


    //Todo: add new
    @PostMapping("/v1/add")
    ResponseEntity<ApiResponse<SuatChieuResponse>> addNew(@RequestBody CreateSuatChieuRequest request);
    //todo: get list


    @GetMapping("/v1/all")
    ResponseEntity<ApiResponse<List<SuatChieuResponse>>> getList();

    //todo: get detail


    @GetMapping("/v1/{id}")
    ResponseEntity<ApiResponse<SuatChieuResponse>> getDetail(@PathVariable(name = "id") String id);


    //todo: update
    @PutMapping("/v1/update")
    ResponseEntity<ApiResponse<SuatChieuResponse>> update(@RequestBody UpdateSuatChieuRequest request);

    //todo : delete

    @DeleteMapping("/v1/{id}")
    ResponseEntity<ApiResponse<CommonStatusResponse>> delete(@PathVariable(name = "id") String id);

}

