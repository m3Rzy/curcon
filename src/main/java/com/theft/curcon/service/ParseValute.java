package com.theft.curcon.service;

import com.theft.curcon.model.Valute;

import java.time.LocalDate;
import java.util.Map;

@FunctionalInterface
public interface ParseValute {
    Map<String, Valute> parse(LocalDate date);
}
