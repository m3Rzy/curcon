package com.theft.curcon.service.data;

import com.theft.curcon.model.Valute;
import com.theft.curcon.service.parser.ParseValute;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class ValuteDataService {

    private final ParseValute parseValute;

    public Valute getByValuteCodeByDate(String charCode, LocalDate date) {
        Valute valute = parseValute.parse(date).get(charCode);
        if (valute == null) {
            throw new IllegalArgumentException("Валюта с кодом " + charCode + " не найдена!");
        }
        log.info("User fetched {} from date {}", charCode, date);
        return valute;
    }

    public Map<String, Valute> getAllValutesByDate(LocalDate date) {
        log.info("User fetched all valutes from date {}", date);
        return parseValute.parse(date);
    }


    /* todo: добавить кэширование + автозапрос к ЦБ РФ за актуальными данными */
//    @Scheduled(fixedRate = 5000) - каждые 5 секунд
    @Scheduled(cron = "0 0 13 * * *", zone = "Europe/Moscow") // - каждый день в 13:00 по МСК
    public void test() {
        System.out.println("Запрос к API Центробанка...");
    }
}
