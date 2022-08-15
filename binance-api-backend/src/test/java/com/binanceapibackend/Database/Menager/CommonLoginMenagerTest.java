package com.binanceapibackend.Database.Menager;

import com.binanceapibackend.DataMap;
import com.binanceapibackend.Database.UIService.RegisterService;
import com.binanceapibackend.ReturnValue;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class CommonLoginMenagerTest {


    //*************************************************************************************************
    //***************************** insertNewUser *****************************************************
    //*************************************************************************************************


    // Wysyłam pusta DataMap
    @Test
    void insertNewUser1() {
        //-------------------------------------- PRZYGOTOWANIE DANYCH ----------------------------------------------------------
        DataMap data = new DataMap();
        //-------------------------------------- ODPALENIE FUNKCJI ------------------------------------------------------------
        DataMap result = new RegisterService().insertNewUser(data);
        //-------------------------------------- SPRAWDZENIE WYNIKÓW ----------------------------------------------------------
        Assert.assertTrue("Powinien być błąd", !result.isResultOk());
        Assert.assertEquals(ReturnValue.Common.WARN, result.get("ERROR_CODE"));
    }

    // Password jest null
    @Test
    void insertNewUser2() {
        //-------------------------------------- PRZYGOTOWANIE DANYCH ----------------------------------------------------------
        DataMap data = new DataMap();
        data.put("LOGIN", "twoj login");
        //-------------------------------------- ODPALENIE FUNKCJI ------------------------------------------------------------
        DataMap result = new RegisterService().insertNewUser(data);
        //-------------------------------------- SPRAWDZENIE WYNIKÓW ----------------------------------------------------------
        Assert.assertTrue("Powinien być błąd", !result.isResultOk());
        Assert.assertEquals(ReturnValue.Common.WARN, result.get("ERROR_CODE"));
    }

    // Password_repeat jest null
    @Test
    void insertNewUser3() {
        //-------------------------------------- PRZYGOTOWANIE DANYCH ----------------------------------------------------------
        DataMap data = new DataMap();
        data.put("LOGIN", "twoj login");
        data.put("PASSWORD", "twoj login");

        //-------------------------------------- ODPALENIE FUNKCJI ------------------------------------------------------------
        DataMap result = new RegisterService().insertNewUser(data);
        //-------------------------------------- SPRAWDZENIE WYNIKÓW ----------------------------------------------------------
        Assert.assertTrue("Powinien być błąd", !result.isResultOk());
        Assert.assertEquals(ReturnValue.Common.WARN, result.get("ERROR_CODE"));
    }

    @Test
    void insertNewUser4() {
        //-------------------------------------- PRZYGOTOWANIE DANYCH ----------------------------------------------------------
        DataMap data = new DataMap();
        data.put("LOGIN", "twoj login");
        data.put("PASSWORD", "twoj haslo");
        data.put("REPEAT_PASSWORD", "twoje powtorzone haslo");
        data.put("USER_DATA", new DataMap());
        //-------------------------------------- ODPALENIE FUNKCJI ------------------------------------------------------------
        DataMap result = new RegisterService().insertNewUser(data);
        //-------------------------------------- SPRAWDZENIE WYNIKÓW ----------------------------------------------------------
        Assert.assertTrue("Powinien być błąd! hasła się nie zgadzają", !result.isResultOk());
        Assert.assertEquals(ReturnValue.Common.NOT_ALLOWED_OPERATION, result.get("ERROR_CODE"));
    }

    //*************************************************************************************************
    //***************************** hashing password **************************************************
    //*************************************************************************************************
/*
    @Test
    void hashingPassword1() {
        //-------------------------------------- PRZYGOTOWANIE DANYCH ----------------------------------------------------------
        String password = "TESTOWE";
        //-------------------------------------- ODPALENIE FUNKCJI ------------------------------------------------------------
        String result = new CommonLoginMenager().hashingPassword(password);
        //-------------------------------------- SPRAWDZENIE WYNIKÓW ----------------------------------------------------------
        Assert.assertNotEquals(password, result);
    }

    @Test
    void hashingPassword2() {
        //-------------------------------------- PRZYGOTOWANIE DANYCH ----------------------------------------------------------
        String password = null;
        //-------------------------------------- ODPALENIE FUNKCJI ------------------------------------------------------------
        String result = new CommonLoginMenager().hashingPassword(password);
        //-------------------------------------- SPRAWDZENIE WYNIKÓW ----------------------------------------------------------
        Assert.assertEquals(null, result);
    }

    @Test
    void hashingPassword3() {
        //-------------------------------------- PRZYGOTOWANIE DANYCH ----------------------------------------------------------
        String password = "";
        //-------------------------------------- ODPALENIE FUNKCJI ------------------------------------------------------------
        String result = new CommonLoginMenager().hashingPassword(password);
        //-------------------------------------- SPRAWDZENIE WYNIKÓW ----------------------------------------------------------
        Assert.assertNotEquals(password, result);
    }

    @Test
    void hashingPassword4() {
        //-------------------------------------- PRZYGOTOWANIE DANYCH ----------------------------------------------------------
        String password = " ";
        //-------------------------------------- ODPALENIE FUNKCJI ------------------------------------------------------------
        String result = new CommonLoginMenager().hashingPassword(password);
        //-------------------------------------- SPRAWDZENIE WYNIKÓW ----------------------------------------------------------
        Assert.assertNotEquals(password, result);
    }

    @Test
    void hashingPassword5() {
        //-------------------------------------- PRZYGOTOWANIE DANYCH ----------------------------------------------------------
        String password = "   ";
        //-------------------------------------- ODPALENIE FUNKCJI ------------------------------------------------------------
        String result = new CommonLoginMenager().hashingPassword(password);
        //-------------------------------------- SPRAWDZENIE WYNIKÓW ----------------------------------------------------------
        Assert.assertNotEquals(password, result);
    }

    @Test
    void hashingPassword6() {
        //-------------------------------------- PRZYGOTOWANIE DANYCH ----------------------------------------------------------
        String password = "haslo123";
        //-------------------------------------- ODPALENIE FUNKCJI ------------------------------------------------------------
        String result = new CommonLoginMenager().hashingPassword(password);
        //-------------------------------------- SPRAWDZENIE WYNIKÓW ----------------------------------------------------------
        Assert.assertNotEquals(password, result);
    }

(

    //*************************************************************************************************
    //********************************** checkNumber **************************************************
    //*************************************************************************************************

    @Test
    void checkNumber1() {
        //-------------------------------------- PRZYGOTOWANIE DANYCH ----------------------------------------------------------
        String number = "haslo123";
        //-------------------------------------- ODPALENIE FUNKCJI ------------------------------------------------------------
        boolean result = new CommonLoginMenager().checkNumber(number);
        //-------------------------------------- SPRAWDZENIE WYNIKÓW ----------------------------------------------------------
        Assert.assertTrue("Nie jest telefonem", !result);
    }

    @Test
    void checkNumber2() {
        //-------------------------------------- PRZYGOTOWANIE DANYCH ----------------------------------------------------------
        String number = "123245";
        //-------------------------------------- ODPALENIE FUNKCJI ------------------------------------------------------------
        boolean result = new CommonLoginMenager().checkNumber(number);
        //-------------------------------------- SPRAWDZENIE WYNIKÓW ----------------------------------------------------------
        Assert.assertTrue("Za malo cyfr jak na numer", !result);
    }

    @Test
    void checkNumber3() {
        //-------------------------------------- PRZYGOTOWANIE DANYCH ----------------------------------------------------------
        String number = "123456789";
        //-------------------------------------- ODPALENIE FUNKCJI ------------------------------------------------------------
        boolean result = new CommonLoginMenager().checkNumber(number);
        //-------------------------------------- SPRAWDZENIE WYNIKÓW ----------------------------------------------------------
        Assert.assertTrue("Prawidlowe dzialanie. To moze byc numer telefonu", result);
    }

    //*************************************************************************************************
    //********************************** isValidEmailAddress ******************************************
    //*************************************************************************************************

    @Test
    void isValidEmailAddress1() {
        //-------------------------------------- PRZYGOTOWANIE DANYCH ----------------------------------------------------------
        String email = "";
        //-------------------------------------- ODPALENIE FUNKCJI ------------------------------------------------------------
        boolean result = new CommonLoginMenager().isValidEmailAddress(email);
        //-------------------------------------- SPRAWDZENIE WYNIKÓW ----------------------------------------------------------
        Assert.assertTrue("to nie jest email", !result);
    }

    @Test
    void isValidEmailAddress2() {
        //-------------------------------------- PRZYGOTOWANIE DANYCH ----------------------------------------------------------
        //-------------------------------------- ODPALENIE FUNKCJI ------------------------------------------------------------
        boolean result = new CommonLoginMenager().isValidEmailAddress(null);
        //-------------------------------------- SPRAWDZENIE WYNIKÓW ----------------------------------------------------------
        Assert.assertTrue("to nie jest email", !result);
    }

    @Test
    void isValidEmailAddress3() {
        //-------------------------------------- PRZYGOTOWANIE DANYCH ----------------------------------------------------------
        String email = "gmail@gmail.com";
        //-------------------------------------- ODPALENIE FUNKCJI ------------------------------------------------------------
        boolean result = new CommonLoginMenager().isValidEmailAddress(email);
        //-------------------------------------- SPRAWDZENIE WYNIKÓW ----------------------------------------------------------
        Assert.assertTrue("to jest email", result);
    }

    @Test
    void isValidEmailAddress4() {
        //-------------------------------------- PRZYGOTOWANIE DANYCH ----------------------------------------------------------
        String email = "g.com.plfdsfs";
        //-------------------------------------- ODPALENIE FUNKCJI ------------------------------------------------------------
        boolean result = new CommonLoginMenager().isValidEmailAddress(email);
        //-------------------------------------- SPRAWDZENIE WYNIKÓW ----------------------------------------------------------
        Assert.assertTrue("to nie jest email", !result);
    }

 */
}