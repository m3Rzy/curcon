package com.theft.curcon.controller;

import com.theft.curcon.service.CurrencyDataService;
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
    public ResponseEntity<?> getAllValutes() {
        log.info("The values were successfully displayed!");
        return ResponseEntity
                .status(201)
                .body(currencyDataService.getValutes());
    }

    @GetMapping("/{valute}")
    public ResponseEntity<?> getByValute(@PathVariable String valute) {
        try {
            log.info("User was displayed {}", valute);
            return ResponseEntity
                    .status(201)
                    .body(currencyDataService.getByValuteCode(valute));
        } catch (Exception e) {
            return ResponseEntity
                    .status(400)
                    .body("Такой валюты не существует!");
        }
    }
}
