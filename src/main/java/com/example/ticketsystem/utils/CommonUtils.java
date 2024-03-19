package com.example.ticketsystem.utils;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Random;

public class CommonUtils {

    private static final Random random = new Random();

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
}
