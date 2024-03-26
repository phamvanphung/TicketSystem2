package com.example.ticketsystem.controler;


import com.example.ticketsystem.dto.common.response.ApiResponse;
import com.example.ticketsystem.dto.common.response.CommonStatusResponse;
import com.example.ticketsystem.dto.suatchieu.request.CreateSuatChieuRequest;
import com.example.ticketsystem.dto.suatchieu.request.UpdateSuatChieuRequest;
import com.example.ticketsystem.dto.suatchieu.response.SuatChieuResponse;
import com.example.ticketsystem.dto.voucher.request.CreateVoucherRequest;
import com.example.ticketsystem.dto.voucher.request.GiveVoucherRequest;
import com.example.ticketsystem.dto.voucher.request.UpdateVoucherRequest;
import com.example.ticketsystem.dto.voucher.response.VoucherResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/voucher")
public interface IVoucherController {


    //Todo: add new
    @PostMapping("/v1/add")
    ResponseEntity<ApiResponse<VoucherResponse>> addNew(@RequestBody CreateVoucherRequest request);
    //todo: get list


    @GetMapping("/v1/all")
    ResponseEntity<ApiResponse<List<VoucherResponse>>> getList();

    //todo: get detail


    @GetMapping("/v1/{id}")
    ResponseEntity<ApiResponse<VoucherResponse>> getDetail(@PathVariable(name = "id") String id);


    //todo: update
    @PutMapping("/v1/update")
    ResponseEntity<ApiResponse<VoucherResponse>> update(@RequestBody UpdateVoucherRequest request);

    //todo : delete

    @DeleteMapping("/v1/{id}")
    ResponseEntity<ApiResponse<CommonStatusResponse>> delete(@PathVariable(name = "id") String id);



    @PutMapping("/v1/give")
    ResponseEntity<ApiResponse<VoucherResponse>> giveVoucher(@RequestBody GiveVoucherRequest request);
}
