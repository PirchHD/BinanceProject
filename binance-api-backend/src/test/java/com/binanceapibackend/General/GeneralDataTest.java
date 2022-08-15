package com.binanceapibackend.General;

import com.binanceapibackend.DataMap;
import com.binanceapibackend.General.Models.ExchangeInfo;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.List;

class GeneralDataTest {

    @Test
    void getExchangeInfo() {
        //-------------------------------------- PRZYGOTOWANIE DANYCH ----------------------------------------------------------
        //-------------------------------------- ODPALENIE FUNKCJI ------------------------------------------------------------
        DataMap result = new GeneralData().getExchangeInfo();
        //-------------------------------------- SPRAWDZENIE WYNIKÓW ----------------------------------------------------------
        Assert.assertTrue("Metoda powinna zadzialac poprawnie", result.isResultOk());
        Assert.assertTrue( result.containsKey("EXCHANG"));
        ExchangeInfo exchangeInfo = (ExchangeInfo) result.get("EXCHANG");
        Assert.assertNotNull(exchangeInfo);
    }

    @Test
    void getSymbols() {
        //-------------------------------------- PRZYGOTOWANIE DANYCH ----------------------------------------------------------
        //-------------------------------------- ODPALENIE FUNKCJI ------------------------------------------------------------
        DataMap result = new GeneralData().getSymbols();
        //-------------------------------------- SPRAWDZENIE WYNIKÓW ----------------------------------------------------------
        Assert.assertTrue("Metoda powinna zadzialac poprawnie", result.isResultOk());
        Assert.assertTrue( result.containsKey("LIST_SYMBOLS"));
        List<String> listSymbols = (List<String>) result.get("LIST_SYMBOLS");
        Assert.assertNotNull(listSymbols);
        Assert.assertEquals(listSymbols.size(), 1938);
    }
}