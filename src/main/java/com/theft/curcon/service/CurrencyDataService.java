package com.theft.curcon.service;

import com.theft.curcon.model.Valute;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;

@Component
public class CurrencyDataService {

    private final ParseValute parseValute;
    @Getter
    private Map<String, Valute> valutes;

    @Autowired
    public CurrencyDataService(ParseValute parseValute) {
        this.parseValute = parseValute;
        this.valutes = parseValute.parse(LocalDate.now());
    }

    public void updateData(LocalDate date) {
        this.valutes = parseValute.parse(date);
    }

    public Valute getByValuteCode(String charCode) {
        Valute valute = valutes.get(charCode);
        if (valute == null) {
            throw new IllegalArgumentException("Валюта с кодом " + charCode + " не найдена!");
        }
        return valute;
    }
}
