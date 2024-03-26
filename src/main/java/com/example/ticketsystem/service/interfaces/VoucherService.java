package com.example.ticketsystem.service.interfaces;

import com.example.ticketsystem.dto.common.response.ApiResponse;
import com.example.ticketsystem.dto.common.response.CommonStatusResponse;
import com.example.ticketsystem.dto.voucher.request.CreateVoucherRequest;
import com.example.ticketsystem.dto.voucher.request.GiveVoucherRequest;
import com.example.ticketsystem.dto.voucher.request.UpdateVoucherRequest;
import com.example.ticketsystem.dto.voucher.response.VoucherResponse;

import java.util.List;

public interface VoucherService {

    ApiResponse<VoucherResponse> addNew(CreateVoucherRequest request);

    ApiResponse<List<VoucherResponse>> getList();

    ApiResponse<VoucherResponse> getDetail(String id);

    ApiResponse<VoucherResponse> update(UpdateVoucherRequest request);
    ApiResponse<CommonStatusResponse> delete(String id);

    ApiResponse<VoucherResponse> giveVoucher(GiveVoucherRequest request);
}
