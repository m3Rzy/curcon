package com.theft.curcon.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Valute {
    private long numCode;
    private String charCode;
    private double value;
}
