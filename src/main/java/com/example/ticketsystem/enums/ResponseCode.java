package com.example.ticketsystem.enums;


import lombok.Getter;

@Getter
public enum ResponseCode {
    SUCCESS(0,"Success"),
    FAILED(1,"failed"),
    INTERNAL_SERVER_ERROR(1001,"Internal server error"),


    USER_EMAIL_EXISTED(10001,"User email existed"),
    USER_NOT_FOUND(10002,"User not found"),
    USER_OTP_NOT_MATCH(10003,"User otp not match"),



    FILE_UPLOAD_FAILED(10101,"File upload failed"),
    FILE_NOT_FOUND(10102,"File not found"),
    FILE_GET_ERROR(10103, "File get Error"),
    FILE_MINIO_ERROR(10104,"Minio error"),



    ;
    private final int code;
    private final String message;

    ResponseCode(int code, String message){
        this.code = code;
        this.message = message;
    }
}
