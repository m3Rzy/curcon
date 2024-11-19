package com.theft.curcon.controller;

import com.theft.curcon.service.ValuteDataServiceImpl;
import com.theft.curcon.util.DateFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.function.Supplier;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class CurconController {

    private final ValuteDataServiceImpl valuteDataService;

    @GetMapping
    public ResponseEntity<?> getAllValutesToday(@RequestParam(required = false) String date) {
        return handleRequest(() ->
                valuteDataService.getAllValutesByDate(DateFormatter.fromStringToDate(date))
        );
    }

    @GetMapping("/{valute}")
    public ResponseEntity<?> getByValute(@PathVariable String valute,
                                         @RequestParam(required = false) String date) {
        return handleRequest(() ->
                valuteDataService.getByValuteCodeByDate(valute.toUpperCase(),
                        DateFormatter.fromStringToDate(date))
        );
    }

    private ResponseEntity<?> handleRequest(Supplier<Object> action) {
        try {
            return ResponseEntity.ok(action.get());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Произошла ошибка: " + e.getMessage());
        }
    }
}
