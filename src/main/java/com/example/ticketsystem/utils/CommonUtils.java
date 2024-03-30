package com.example.ticketsystem.utils;

import com.example.ticketsystem.enums.ResponseCode;
import com.example.ticketsystem.exceptions.BusinessException;
import lombok.SneakyThrows;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Random;

public class CommonUtils {

    private static final Random random = new Random();
    private static final String HMAC_SHA512 = "HmacSHA512";

    public static LocalDateTime parseDateTime(String dob) throws DateTimeParseException {
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(dob,dateTimeFormatter).atStartOfDay();
        } catch (DateTimeParseException e) {
            throw e;
        }
    }


    public static String getOtp() {
        int r = random.nextInt(999998) + 1;
        return String.format("%06d",r);
    }

    public static String getCodeVoucher() {
        long r = random.nextLong(999999998) + 1;
        return String.format("%09d",r);
    }



    public static String getSecureHash(String secret, String value) {
        Mac sha512Hmac;
        String result;
        final String key = secret;

        try {
            final byte[] byteKey = key.getBytes(StandardCharsets.UTF_8);
            sha512Hmac = Mac.getInstance(HMAC_SHA512);
            SecretKeySpec keySpec = new SecretKeySpec(byteKey, HMAC_SHA512);
            sha512Hmac.init(keySpec);
            byte[] macData = sha512Hmac.doFinal(value.getBytes(StandardCharsets.UTF_8));

            // Can either base64 encode or put it right into hex
//            result = Base64.getEncoder().encodeToString(macData);
            return bytesToHex(macData);
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new BusinessException(ResponseCode.ORDERS_HASH_VALUE_FAILED);
        }
    }



    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}
