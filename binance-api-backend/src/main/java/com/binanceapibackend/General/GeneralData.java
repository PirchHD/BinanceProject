package com.binanceapibackend.General;

import com.binanceapibackend.General.Models.ExchangeInfo;
import com.binanceapibackend.General.Models.SymbolInfo;
import com.binanceapibackend.BinanceApiConnection;
import com.binanceapibackend.DataMap;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@RestController
public class GeneralData extends BinanceApiConnection {

    public GeneralData() {
        super();
    }

    @GetMapping("/getExchangeInfo")
    public DataMap getExchangeInfo(){
        DataMap result = new DataMap();

        try {

            URL url = new URL(URL_BINANCE_API + "/api/v1/exchangeInfo");
            InputStreamReader reader = new InputStreamReader(url.openStream());
            ExchangeInfo exchangeInfo = new Gson().fromJson(reader, ExchangeInfo.class);
            result.put("EXCHANG", exchangeInfo);

        }catch (IOException e){
            result.setResultError();
        }

        result.setResultOk();

        return result;
    }

    @GetMapping("/getSymbols")
    public DataMap getSymbols(){
        DataMap result = new DataMap();
        List<String> listSymbols = new ArrayList<>();

        try {

            URL url = new URL(URL_BINANCE_API + "/api/v1/exchangeInfo");
            InputStreamReader reader = new InputStreamReader(url.openStream());
            ExchangeInfo exchangeInfo = new Gson().fromJson(reader, ExchangeInfo.class);
            List<SymbolInfo> symbolInfo = exchangeInfo.getSymbols();

            for (SymbolInfo temp: symbolInfo)
                listSymbols.add(temp.getSymbol());

            result.put("LIST_SYMBOLS", listSymbols);

        }catch (IOException e){
            result.setResultError();
        }

        result.setResultOk();

        return result;
    }


}
