package com.example.ticketsystem.controler;


import com.example.ticketsystem.dto.common.response.ApiResponse;
import com.example.ticketsystem.dto.common.response.CommonStatusResponse;
import com.example.ticketsystem.dto.orders.request.CallbackPayment;
import com.example.ticketsystem.dto.orders.request.CreateOrderRequest;
import com.example.ticketsystem.dto.orders.request.PaymentOrderRequest;
import com.example.ticketsystem.dto.orders.request.UpdateOrderRequest;
import com.example.ticketsystem.dto.orders.response.CallbackResponse;
import com.example.ticketsystem.dto.orders.response.OrderResponse;
import com.example.ticketsystem.dto.orders.response.PaymentOrderResponse;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/orders")
public interface IOrdersController {


    //Todo: add new
    @PostMapping("/v1/add")
    ResponseEntity<ApiResponse<OrderResponse>> addNew(@RequestBody CreateOrderRequest request);
    //todo: get list


    @GetMapping("/v1/all")
    ResponseEntity<ApiResponse<List<OrderResponse>>> getList();

    //todo: get detail


    @GetMapping("/v1/{id}")
    ResponseEntity<ApiResponse<OrderResponse>> getDetail(@PathVariable(name = "id") String id);


    //todo: update
    @PutMapping("/v1/update")
    ResponseEntity<ApiResponse<OrderResponse>> update(@RequestBody UpdateOrderRequest request);

    //todo : delete

    @DeleteMapping("/v1/{id}")
    ResponseEntity<ApiResponse<CommonStatusResponse>> delete(@PathVariable(name = "id") String id);


    @PostMapping("/v1/payment")
    ResponseEntity<ApiResponse<PaymentOrderResponse>> payment(@RequestBody PaymentOrderRequest request);



    @GetMapping("/v1/callback")
    ResponseEntity<CallbackResponse> callback(@ParameterObject  CallbackPayment request);

}
