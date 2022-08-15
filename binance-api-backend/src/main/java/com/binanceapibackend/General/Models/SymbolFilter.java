package com.binanceapibackend.General.Models;

import org.springframework.context.annotation.FilterType;

public class SymbolFilter {

    private FilterType filterType;

    private String minPrice;

    private String maxPrice;

    private String tickSize;

    private String minQty;

    private String maxQty;

    private String stepSize;

    // MIN_NOTIONAL

    private String minNotional;


    // MAX_NUM_ALGO_ORDERS

    private String maxNumAlgoOrders;


    private String limit;
}
