package com.binanceapibackend.Market;

import com.binanceapibackend.BinanceApiConnection;
import com.binanceapibackend.DataMap;
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
public class MarketData extends BinanceApiConnection {

    public MarketData(){
        super();
    }


    /**
     * @Description
     * Dzięki tej metodzie zwracam aktualna cene kryptowaluty
     */
    @GetMapping("/getSymbolPriceTicker/{symbol}")
    public DataMap getSymbolPriceTicker(@PathVariable String symbol){
        DataMap result = new DataMap();
        try {
            URL url = new URL(URL_BINANCE_API + "/api/v3/ticker/price?symbol=" + symbol);
            InputStreamReader reader = new InputStreamReader(url.openStream());

            Price price = new Gson().fromJson(reader, Price.class);
            result.put("SYMBOL", price.getSymbol());
            result.put("CURRENT_PRICE", price.getPrice());
            result.put("DATE", price.getDate());

            result.setResultOk();
        }catch (Exception e){
            result.setResultError();
        }

        return result;
    }

    @GetMapping("/getCandlestickData/{interval}/{symbol}/{limit}/{startTime}/{endTime}")
    public ArrayList getCandlestickData(@PathVariable String symbol, @PathVariable String interval, @PathVariable String limit,
                                        @PathVariable String startTime, @PathVariable String endTime) throws IOException{

        URL url = new URL(URL_BINANCE_API + "/api/v3/klines?symbol=" + symbol +
                "&interval=" + interval +
                "&startTime=" + startTime +
                "&endTime=" + endTime +
                "&limit=" + limit);

        InputStreamReader reader = new InputStreamReader(url.openStream());
        ArrayList candlestick = new Gson().fromJson(reader, ArrayList.class);
        ArrayList<Candlestick> tempArray = new ArrayList<>();
        for (int i = 0; i < candlestick.size(); i++){
            ArrayList temp = (ArrayList) candlestick.get(i);
            Candlestick tempCandle = new Candlestick(temp);
            tempArray.add(tempCandle);
        }

        return tempArray;
    }

    @GetMapping("/getCandlestickData/{interval}/{symbol}/{startTime}/{endTime}")
    public ArrayList getCandlestickData(@PathVariable String symbol, @PathVariable CandlestickInterval interval,
                                        @PathVariable String startTime, @PathVariable String endTime){
        ArrayList<Candlestick> tempArray = new ArrayList<>();

        try {
            URL url = new URL(URL_BINANCE_API + "/api/v3/klines?symbol=" + symbol +
                    "&interval=" + interval.getIntervalId() +
                    "&startTime=" + startTime +
                    "&endTime=" + endTime +
                    "&limit=" + "9999999");

            InputStreamReader reader = new InputStreamReader(url.openStream());
            ArrayList candlestick = new Gson().fromJson(reader, ArrayList.class);

            for (int i = 0; i < candlestick.size(); i++) {
                ArrayList temp = (ArrayList) candlestick.get(i);
                Candlestick tempCandle = new Candlestick(temp);
                tempArray.add(tempCandle);
            }
        }catch (IOException e){}

        return tempArray;
    }

    @GetMapping("/getCandlestickData/{interval}/{symbol}/{limit}")
    public ArrayList getCandlestickData(@PathVariable String symbol, @PathVariable CandlestickInterval interval, @PathVariable String limit){

        DataMap result = new DataMap();
        ArrayList<Candlestick> tempArray = null;
        try {
            URL url = new URL(URL_BINANCE_API + "/api/v3/klines?symbol=" + symbol +
                    "&interval=" + interval.getIntervalId() +
                    "&limit=" + limit);

            InputStreamReader reader = new InputStreamReader(url.openStream());
            ArrayList candlestick = new Gson().fromJson(reader, ArrayList.class);
            tempArray = new ArrayList<>();
            for (Object o : candlestick) {
                ArrayList temp = (ArrayList) o;
                Candlestick tempCandle = new Candlestick(temp);
                tempArray.add(tempCandle);
            }
        }catch (IOException e){
            result.setResultError();
        }

        return tempArray;
    }

    @GetMapping("/getCandlestickData/{interval}/{symbol}")
    public ArrayList getCandlestickData(@PathVariable String symbol, @PathVariable CandlestickInterval interval){

        DataMap result = new DataMap();
        ArrayList<Candlestick> tempArray = null;
        try {
            URL url = new URL(URL_BINANCE_API + "/api/v3/klines?symbol=" + symbol +
                    "&interval=" + interval.getIntervalId() +
                    "&limit=" + "9999999");

            InputStreamReader reader = new InputStreamReader(url.openStream());
            ArrayList candlestick = new Gson().fromJson(reader, ArrayList.class);
            tempArray = new ArrayList<>();
            for (Object o : candlestick) {
                ArrayList temp = (ArrayList) o;
                Candlestick tempCandle = new Candlestick(temp);
                tempArray.add(tempCandle);
            }
        }catch (IOException e){
            result.setResultError();
        }

        return tempArray;
    }

    public DataMap getPercentageChange(String symbol, String interval) {
        DataMap result = new DataMap();
        try {

            URL url = new URL(URL_BINANCE_API + "/api/v3/klines?symbol=" + symbol +
                    "&interval=" + interval +
                    "&limit=" + "1");
            InputStreamReader reader = new InputStreamReader(url.openStream());
            ArrayList candlestick = new Gson().fromJson(reader, ArrayList.class);

            if(candlestick.size() > 1)
                return null;

            ArrayList<Candlestick> candleArray = (ArrayList<Candlestick>) candlestick.get(0);
            Candlestick candle = new Candlestick(candleArray);


            if(candle == null || candle.getClose() == null || candle.getOpen() == null )
                return null;

            Double close = Double.valueOf(candle.getClose());
            Double open = Double.valueOf(candle.getOpen());
            Double change = (close * 100) / open;

            change = change - 100;

            result.put("CHANGE", change);
            result.put("SYMBOL", symbol);
            result.setResultOk();
        }catch (IOException e){
            result.setResultError();
        }

        return result;
    }


}
