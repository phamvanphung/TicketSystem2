package com.example.ticketsystem.dto.common.response;


import com.example.ticketsystem.enums.ResponseCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.coyote.Response;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ApiResponse<T> {
    private int code;
    private String message;
    private T data;



    public ApiResponse<T> ok(T data){
        this.setCode(ResponseCode.SUCCESS.getCode());
        this.setMessage(ResponseCode.SUCCESS.getMessage());
        this.setData(data);
        return this;
    }



    public ApiResponse<?> error(ResponseCode error){
        this.setCode(error.getCode());
        this.setMessage(error.getMessage());
        return this;
    }
}
