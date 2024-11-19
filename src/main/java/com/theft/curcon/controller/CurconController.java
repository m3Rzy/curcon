package com.theft.curcon.controller;

import com.theft.curcon.service.CurrencyDataService;
import com.theft.curcon.util.DateFormatter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@Slf4j
public class CurconController {

    private CurrencyDataService currencyDataService;

    @GetMapping
    public ResponseEntity<?> getAllValutesToday(@RequestParam String date) {
        try {
            return ResponseEntity
                    .status(201)
                    .body(currencyDataService.getAllValutesByDate(
                            DateFormatter
                                    .fromStringToDate(date)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(404)
                    .body("Неверный формат даты!");
        }
    }

    @GetMapping("/{valute}")
    public ResponseEntity<?> getByValute(@PathVariable String valute, @RequestParam String date) {
        try {
            return ResponseEntity
                    .status(201)
                    .body(currencyDataService.getByValuteCodeByDate(valute.toUpperCase(), DateFormatter
                            .fromStringToDate(date)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(404)
                    .body("Неверный формат даты!");
        } catch (Exception e) {
            return ResponseEntity
                    .status(404)
                    .body("Такой валюты не существует!");
        }
    }
}
