package com.example.ticketsystem.exceptions;


import com.example.ticketsystem.dto.common.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BusinessException.class})
    public ResponseEntity<ApiResponse<?>> businessExceptionHandler(BusinessException e){
        return new ResponseEntity<ApiResponse<?>>(new ApiResponse().error(e.getError()), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiResponse<?>> exceptionHandler(Exception e){
        return new ResponseEntity<ApiResponse<?>>(new ApiResponse().setMessage(e.getMessage()).setCode(1), HttpStatus.BAD_REQUEST);
    }
}
