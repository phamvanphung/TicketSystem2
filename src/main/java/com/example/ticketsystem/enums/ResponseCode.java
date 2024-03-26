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




    SUAT_CHIEU_ADD_FAILED(10201,"Suat chieu add failed"),
    SUAT_CHIEU_NOT_FOUND(10202,"Suat chieu not found"),



    FILM_NOT_FOUND(10301,"Film not found"),
    FILM_EXISTED(10302,"Film existed"),

    VOUCHER_NOT_FOUND(10401,"Voucher Not Found"),
    VOUCHER_USED(10402,"Voucher used"),
    VOUCHER_USER_DO_NOT_PERMISSION(10403,"Voucher user do not permission"),


    ORDERS_NOT_FOUND(10501, "Order not found"),



    ;
    private final int code;
    private final String message;

    ResponseCode(int code, String message){
        this.code = code;
        this.message = message;
    }
}
