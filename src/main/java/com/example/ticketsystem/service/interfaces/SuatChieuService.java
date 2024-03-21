package com.example.ticketsystem.service.interfaces;

import com.example.ticketsystem.dto.common.response.ApiResponse;
import com.example.ticketsystem.dto.common.response.CommonStatusResponse;
import com.example.ticketsystem.dto.suatchieu.request.CreateSuatChieuRequest;
import com.example.ticketsystem.dto.suatchieu.request.UpdateSuatChieuRequest;
import com.example.ticketsystem.dto.suatchieu.response.SuatChieuResponse;

import java.util.List;

public interface SuatChieuService {


    ApiResponse<SuatChieuResponse> addNew(CreateSuatChieuRequest request);
    ApiResponse<List<SuatChieuResponse>> getList();
    ApiResponse<SuatChieuResponse> getDetail (String id);
    ApiResponse<SuatChieuResponse> update (UpdateSuatChieuRequest request);
    ApiResponse<CommonStatusResponse> delete(String id);

}
