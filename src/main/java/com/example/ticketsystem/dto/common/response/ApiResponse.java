package com.example.ticketsystem.dto.common.response;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ApiResponse<T> {
    private int code;
    private String message;
    private T data;
}
