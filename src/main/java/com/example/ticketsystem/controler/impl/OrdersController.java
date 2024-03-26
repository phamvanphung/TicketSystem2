package com.example.ticketsystem.controler.impl;

import com.example.ticketsystem.controler.IOrdersController;
import com.example.ticketsystem.dto.common.response.ApiResponse;
import com.example.ticketsystem.dto.common.response.CommonStatusResponse;
import com.example.ticketsystem.dto.orders.request.CreateOrderRequest;
import com.example.ticketsystem.dto.orders.request.UpdateOrderRequest;
import com.example.ticketsystem.dto.orders.response.OrderResponse;
import com.example.ticketsystem.dto.voucher.response.VoucherResponse;
import com.example.ticketsystem.service.OrderServiceImpl;
import com.example.ticketsystem.service.interfaces.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class OrdersController implements IOrdersController {

    private final OrdersService ordersService;

    @Override
    public ResponseEntity<ApiResponse<OrderResponse>> addNew(CreateOrderRequest request) {
        ApiResponse<OrderResponse> response = ordersService.addNew(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getList() {
        ApiResponse<List<OrderResponse>> response = ordersService.getList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<OrderResponse>> getDetail(String id) {
        ApiResponse<OrderResponse> response = ordersService.getDetail(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<OrderResponse>> update(UpdateOrderRequest request) {
        ApiResponse<OrderResponse> response = ordersService.update(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<CommonStatusResponse>> delete(String id) {
        ApiResponse<CommonStatusResponse> response = ordersService.delete(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
