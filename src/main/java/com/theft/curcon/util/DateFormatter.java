package com.theft.curcon.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateFormatter {

    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("ddMMyyyy");

    public static LocalDate fromStringToDate(String dateString) {
        if (dateString == null || dateString.isBlank()) {
            return LocalDate.now();
        }
        if (LocalDate.parse(dateString, INPUT_FORMATTER).isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("К сожалению, про будущее мы не знаем...");
        }
        try {
            return LocalDate.parse(dateString, INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Некорректный формат даты: " + dateString, e);
        }
    }
}
