package com.example.ticketsystem.exceptions;


import com.example.ticketsystem.enums.ResponseCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessException extends RuntimeException{
    private ResponseCode error;

    public BusinessException (ResponseCode error) {
        super(error.getMessage());
        this.error = error;
    }
}
