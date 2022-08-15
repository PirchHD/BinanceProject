package com.binanceapibackend.Market.Models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
public class Price{

    private String symbol;
    private BigDecimal price;
    private Date date;

    public Price() {
        this.date = new Date();
    }

    @Override
    public String toString() {
        return "symbol='" + symbol + '\'' +
                ", price=" + price +
                '}';
    }

}
