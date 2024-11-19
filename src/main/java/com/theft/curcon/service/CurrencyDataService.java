package com.theft.curcon.service;

import com.theft.curcon.model.Valute;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CurrencyDataService {

    private final ParseValute parseValute;

    public Valute getByValuteCodeByDate(String charCode, LocalDate date) {
        Valute valute = parseValute.parse(date).get(charCode);
        if (valute == null) {
            throw new IllegalArgumentException("Валюта с кодом " + charCode + " не найдена!");
        }
        return valute;
    }

    public Map<String, Valute> getAllValutesByDate(LocalDate date) {
        return parseValute.parse(date);
    }
}
