package com.binanceapibackend;

import com.binanceapibackend.Market.Models.Candlestick;
import com.binanceapibackend.Market.Models.CandlestickInterval;
import com.binanceapibackend.Market.Models.Price;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

@RestController
public class BinanceApiConnection {

    public static String URL_BINANCE_API = "https://api.binance.com";
    public static String URL_BINANCE_API_V1 = "https://api1.binance.com";
    public static String URL_BINANCE_API_V2 = "https://api2.binance.com";
    public static String URL_BINANCE_API_V3 = "https://api3.binance.com";

    public BinanceApiConnection(){
        if(!ping()){
            System.exit(-1);
        }
    }

    public boolean ping(){
        try {
            URL url = new URL(URL_BINANCE_API + "/api/v1/ping");
            InputStreamReader reader = new InputStreamReader(url.openStream());
            Object obj = new Gson().fromJson(reader, Object.class);

            if (obj == null)
                return false;

        }catch (Exception e){
            return false;
        }

        return true;
    }

}
