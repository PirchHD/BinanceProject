package com.binanceapibackend.Database.Menager;

import com.binanceapibackend.DataMap;
import com.binanceapibackend.ReturnValue;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class SettingsManagerTest {

    /*
    @Test
    void update1() {
        //-------------------------------------- PRZYGOTOWANIE DANYCH ----------------------------------------------------------
        DataMap data = new DataMap();
        //-------------------------------------- ODPALENIE FUNKCJI ------------------------------------------------------------
        DataMap result = new SettingsManager().update(data);
        //-------------------------------------- SPRAWDZENIE WYNIKÓW ----------------------------------------------------------
        Assert.assertFalse("Powinien być błąd", result.isResultOk());
        Assert.assertEquals(ReturnValue.Common.WARN, result.get("ERROR_CODE"));
    }

    @Test
    void update2() {
        //-------------------------------------- PRZYGOTOWANIE DANYCH ----------------------------------------------------------
        DataMap data = new DataMap();
        data.put("USERNAME", "TEST");
        //-------------------------------------- ODPALENIE FUNKCJI ------------------------------------------------------------
        DataMap result = new SettingsManager().update(data);
        //-------------------------------------- SPRAWDZENIE WYNIKÓW ----------------------------------------------------------
        Assert.assertTrue("Powinien być błąd", !result.isResultOk());
        Assert.assertEquals(ReturnValue.Common.WARN, result.get("ERROR_CODE"));
    }

    @Test
    void update3() {
        //-------------------------------------- PRZYGOTOWANIE DANYCH ----------------------------------------------------------
        DataMap data = new DataMap();
        data.put("USERNAME", "TEST");
        data.put("NAME", "IMIE");
        //-------------------------------------- ODPALENIE FUNKCJI ------------------------------------------------------------
        DataMap result = new SettingsManager().update(data);
        //-------------------------------------- SPRAWDZENIE WYNIKÓW ----------------------------------------------------------
        Assert.assertTrue("Powinien być błąd", !result.isResultOk());
        Assert.assertEquals(ReturnValue.Common.WARN, result.get("ERROR_CODE"));
    }

    @Test
    void update4() {
        //-------------------------------------- PRZYGOTOWANIE DANYCH ----------------------------------------------------------
        DataMap data = new DataMap();
        data.put("USERNAME", "TEST");
        data.put("NAME", "IMIE");
        data.put("LASTNAME", "NAZWISKO");
        //-------------------------------------- ODPALENIE FUNKCJI ------------------------------------------------------------
        DataMap result = new SettingsManager().update(data);
        //-------------------------------------- SPRAWDZENIE WYNIKÓW ----------------------------------------------------------
        Assert.assertTrue("Powinien być błąd", !result.isResultOk());
        Assert.assertEquals(ReturnValue.Common.WARN, result.get("ERROR_CODE"));
    }

    @Test
    void update5() {
        //-------------------------------------- PRZYGOTOWANIE DANYCH ----------------------------------------------------------
        DataMap data = new DataMap();
        data.put("USERNAME", "TEST");
        data.put("NAME", "IMIE");
        data.put("LASTNAME", "NAZWISKO");
        data.put("LOGIN_ID", 123);

        //-------------------------------------- ODPALENIE FUNKCJI ------------------------------------------------------------
        DataMap result = new SettingsManager().update(data);
        //-------------------------------------- SPRAWDZENIE WYNIKÓW ----------------------------------------------------------
        Assert.assertTrue("Powinien być błąd", !result.isResultOk());
        Assert.assertEquals(ReturnValue.Common.NOT_ALLOWED_OPERATION, result.get("ERROR_CODE"));
    }

    @Test
    void update6() {
        //-------------------------------------- PRZYGOTOWANIE DANYCH ----------------------------------------------------------
        DataMap data = new DataMap();
        data.put("USERNAME", "TEST");
        data.put("NAME", "IMIE");
        data.put("LASTNAME", "NAZWISKO");
        data.put("LOGIN_ID", 123);
        data.put("PHONE_NUMBER", 123456789);

        //-------------------------------------- ODPALENIE FUNKCJI ------------------------------------------------------------
        DataMap result = new SettingsManager().update(data);
        //-------------------------------------- SPRAWDZENIE WYNIKÓW ----------------------------------------------------------
        Assert.assertTrue("Powinien być błąd", !result.isResultOk());
    }

     */
}