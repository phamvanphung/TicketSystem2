package com.example.ticketsystem.service.interfaces;

import com.example.ticketsystem.dto.common.response.ApiResponse;
import com.example.ticketsystem.dto.common.response.CommonStatusResponse;
import com.example.ticketsystem.dto.orders.request.CreateOrderRequest;
import com.example.ticketsystem.dto.orders.request.UpdateOrderRequest;
import com.example.ticketsystem.dto.orders.response.OrderResponse;

import java.util.List;

public interface OrdersService {


    ApiResponse<OrderResponse> addNew(CreateOrderRequest request);

    ApiResponse<List<OrderResponse>> getList();

    ApiResponse<OrderResponse> getDetail(String id);

    ApiResponse<OrderResponse> update(UpdateOrderRequest request);

    ApiResponse<CommonStatusResponse> delete(String id);
}
