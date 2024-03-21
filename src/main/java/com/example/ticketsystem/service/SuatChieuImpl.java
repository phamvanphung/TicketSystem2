package com.example.ticketsystem.service;


import com.example.ticketsystem.dto.common.response.ApiResponse;
import com.example.ticketsystem.dto.common.response.CommonStatusResponse;
import com.example.ticketsystem.dto.suatchieu.request.CreateSuatChieuRequest;
import com.example.ticketsystem.dto.suatchieu.request.UpdateSuatChieuRequest;
import com.example.ticketsystem.dto.suatchieu.response.SuatChieuResponse;
import com.example.ticketsystem.entity.SuatChieu;
import com.example.ticketsystem.enums.ResponseCode;
import com.example.ticketsystem.exceptions.BusinessException;
import com.example.ticketsystem.repository.SuatChieuRepository;
import com.example.ticketsystem.service.interfaces.SuatChieuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SuatChieuImpl implements SuatChieuService {


    private final SuatChieuRepository suatChieuRepository;

    @Override
    public ApiResponse<SuatChieuResponse> addNew(CreateSuatChieuRequest request) {
        try {

            LocalTime lt = LocalTime.parse(request.getGioBatDau(), DateTimeFormatter.ofPattern("HH:mm"));
            SuatChieu suatChieu = new SuatChieu()
                    .setCreatedAt(LocalDateTime.now())
                    .setGioBatDau(lt)
                    .setName(request.getName());
            suatChieu = suatChieuRepository.save(suatChieu);
            return new ApiResponse<SuatChieuResponse>().ok(new SuatChieuResponse(suatChieu));
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ResponseCode.SUAT_CHIEU_ADD_FAILED);
        }
    }

    @Override
    public ApiResponse<List<SuatChieuResponse>> getList() {
        try {
           List<SuatChieu> suatChieuList = suatChieuRepository.findAll();
           List<SuatChieuResponse> response = suatChieuList.stream().map(SuatChieuResponse::new).collect(Collectors.toList());
           return new ApiResponse<List<SuatChieuResponse>>().ok(response);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<SuatChieuResponse> getDetail(String id) {
        try {
           SuatChieu suatChieu = suatChieuRepository.findById(UUID.fromString(id)).orElseThrow(
                   () -> new BusinessException(ResponseCode.SUAT_CHIEU_NOT_FOUND)
           );

            return new ApiResponse<SuatChieuResponse>().ok(new SuatChieuResponse(suatChieu));

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<SuatChieuResponse> update(UpdateSuatChieuRequest request) {
        try {
            SuatChieu suatChieu = suatChieuRepository.findById(UUID.fromString(request.getId())).orElseThrow(
                    () -> new BusinessException(ResponseCode.SUAT_CHIEU_NOT_FOUND)
            );


            if(StringUtils.hasText(request.getName())){
                suatChieu.setName(request.getName());
            }

            if(StringUtils.hasText(request.getGioBatDau())){
                LocalTime lt = LocalTime.parse(request.getGioBatDau(), DateTimeFormatter.ofPattern("HH:mm"));
                suatChieu.setGioBatDau(lt);
            }

            suatChieu = suatChieuRepository.save(suatChieu);
            return new ApiResponse<SuatChieuResponse>().ok(new SuatChieuResponse(suatChieu));

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
            SuatChieu suatChieu = suatChieuRepository.findById(UUID.fromString(id)).orElseThrow(
                    () -> new BusinessException(ResponseCode.SUAT_CHIEU_NOT_FOUND)
            );

            suatChieuRepository.delete(suatChieu);
            return new ApiResponse<CommonStatusResponse>().ok(new CommonStatusResponse(true));

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }
}
