package com.binanceapibackend.Market.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Candlestick{
    String openTime;
    String open;
    String high;
    String low;
    String close;
    String volume;
    String closeTime;
    String quoteAssetVolume;
    Double numberOfTrades;
    String takerBuyBaseAssetVolume;
    String takerBuyBaseQuoteAssetVolume;
    String ignore;

    public Candlestick(ArrayList temp) {
        this.openTime = String.format("%.0f", Double.parseDouble(String.valueOf(temp.get(0))));
        this.open = (String) temp.get(1);
        this.high = (String) temp.get(2);
        this.low = (String) temp.get(3);
        this.close = (String) temp.get(4);
        this.volume = (String) temp.get(5);
        this.closeTime = String.format("%.0f", Double.parseDouble(String.valueOf(temp.get(6))));
        this.quoteAssetVolume = (String) temp.get(7);
        this.numberOfTrades = (Double) temp.get(8);
        this.takerBuyBaseAssetVolume = (String) temp.get(9);
        this.takerBuyBaseQuoteAssetVolume = (String) temp.get(10);
        this.ignore = (String) temp.get(11);
    }

}
