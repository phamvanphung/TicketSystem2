package com.example.ticketsystem.controler.impl;

import com.example.ticketsystem.controler.IVoucherController;
import com.example.ticketsystem.dto.common.response.ApiResponse;
import com.example.ticketsystem.dto.common.response.CommonStatusResponse;
import com.example.ticketsystem.dto.voucher.request.CreateVoucherRequest;
import com.example.ticketsystem.dto.voucher.request.GiveVoucherRequest;
import com.example.ticketsystem.dto.voucher.request.UpdateVoucherRequest;
import com.example.ticketsystem.dto.voucher.response.VoucherResponse;
import com.example.ticketsystem.service.interfaces.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class VoucherController implements IVoucherController {

    private final VoucherService voucherService;


    @Override
    public ResponseEntity<ApiResponse<VoucherResponse>> addNew(CreateVoucherRequest request) {
        ApiResponse<VoucherResponse> response = voucherService.addNew(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<List<VoucherResponse>>> getList() {
        ApiResponse<List<VoucherResponse>> response = voucherService.getList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<VoucherResponse>> getDetail(String id) {
        ApiResponse<VoucherResponse> response = voucherService.getDetail(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<VoucherResponse>> update(UpdateVoucherRequest request) {
        ApiResponse<VoucherResponse> response = voucherService.update(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<CommonStatusResponse>> delete(String id) {
        ApiResponse<CommonStatusResponse> response = voucherService.delete(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<VoucherResponse>> giveVoucher(GiveVoucherRequest request) {
        ApiResponse<VoucherResponse> response = voucherService.giveVoucher(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
