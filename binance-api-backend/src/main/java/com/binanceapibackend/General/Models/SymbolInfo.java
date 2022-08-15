package com.binanceapibackend.General.Models;

import com.binanceapibackend.Market.Models.OrderType;
import lombok.Getter;

import java.util.List;

@Getter
public class SymbolInfo {
    private String symbol;

    private SymbolStatus status;

    private String baseAsset;

    private Integer baseAssetPrecision;

    private String quoteAsset;

    private Integer quotePrecision;

    private List<OrderType> orderTypes;

    private boolean icebergAllowed;

    private boolean ocoAllowed;

    private boolean quoteOrderQtyMarketAllowed;

    private boolean isSpotTradingAllowed;

    private boolean isMarginTradingAllowed;

    private List<SymbolFilter> filters;
}
