package com.theft.curcon.service.parser.impl;

import com.theft.curcon.model.Valute;
import com.theft.curcon.service.parser.ParseValute;
import org.springframework.stereotype.Component;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
public class ParseValueFromXMLService implements ParseValute {

    @Override
    public Map<String, Valute> parse(LocalDate date) {
        try {
            String xmlContent = fetchCurrencyData(date);
            return parseXmlContent(xmlContent);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка парсинга данных валют", e);
        }
    }

    private String fetchCurrencyData(LocalDate date) throws Exception {
        String formattedDate = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String url = "https://cbr.ru/scripts/XML_daily.asp?date_req=" + formattedDate;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();

        HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
        return new String(response.body(), "Windows-1251");
    }

    private Map<String, Valute> parseXmlContent(String xmlContent) throws Exception {
        Document document = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(new ByteArrayInputStream(xmlContent.getBytes(StandardCharsets.UTF_8)));

        document.getDocumentElement().normalize();
        NodeList valuteNodes = document.getElementsByTagName("Valute");

        Map<String, Valute> valutes = new HashMap<>();
        for (int i = 0; i < valuteNodes.getLength(); i++) {
            Element element = (Element) valuteNodes.item(i);
            Valute valute = parseValute(element);
            valutes.put(valute.getCharCode(), valute);
        }
        return valutes;
    }

    private Valute parseValute(Element element) {
        String numCode = getTextContent(element, "NumCode");
        String charCode = getTextContent(element, "CharCode");
        double value = Double.parseDouble(getTextContent(element, "Value").replace(",", "."));

        return Valute.builder()
                .numCode(Long.parseLong(numCode))
                .charCode(charCode)
                .value(value)
                .build();
    }

    private String getTextContent(Element element, String tagName) {
        return element.getElementsByTagName(tagName).item(0).getTextContent();
    }
}
