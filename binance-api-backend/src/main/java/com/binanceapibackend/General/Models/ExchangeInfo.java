package com.binanceapibackend.General.Models;

import lombok.Data;

import java.util.List;

@Data
public class ExchangeInfo {
    private String timezone;

    private Long serverTime;

    private List<RateLimit> rateLimits;

    private List<String> exchangeFilters;

    private List<SymbolInfo> symbols;
}
