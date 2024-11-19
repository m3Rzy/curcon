package com.theft.curcon.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormatter {
    public static LocalDate fromStringToDate(String dateString) {
        try {
            if (dateString.isEmpty() || dateString.isBlank()) {
                return LocalDate.now();
            }
            // Оригинальный формат строки
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("ddMMyyyy");
            // Преобразование даты в строку нужного формата
            return LocalDate.parse(dateString, inputFormatter);
        } catch (Exception e) {
            throw new IllegalArgumentException("Некорректный формат даты: " + dateString, e);
        }
    }
}
