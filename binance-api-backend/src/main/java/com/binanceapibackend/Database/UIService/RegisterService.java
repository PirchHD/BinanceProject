package com.binanceapibackend.Database.UIService;

import com.binanceapibackend.DataMap;
import com.binanceapibackend.Database.Common;
import com.binanceapibackend.Database.Database;
import com.binanceapibackend.Database.Menager.RegisterMenager;
import com.binanceapibackend.ReturnValue;

public class RegisterService {

    /**
     * Metoda tworzy nowego użytkownika
     *
     * Przyjmuję atrybuty:
     *      LOGIN               - (String) login uzytkownika
     *      PASSWORD            - (String) hasło użytkownika
     *      REPEAT_PASSWORD     - (String) powtórzone hasło
     *      USER_DATA (DataMap) - (DataMap) Dane użytkownika takie jak imie/email i itp
     *          NAME         - (String) imie
     *          LAST_NAME    - (String) nazwisko
     *          EMAIL        - (String) emajl
     *          PHONE_NUMBER - (String) numer telefonu
     *
     *  Powinien zwrócić result (DataMap):
     *      Gdy zadziała poprawnie :
     *              RESULT = "OK"
     *
     *      Gdy zauważy błędy:
     *              RESULT = "ERROR"
     *
     * **/
    public DataMap insertNewUser(DataMap data)
    {
        DataMap result = new DataMap();
        Database db = Database.getInstace();
        try
        {
            ReturnValue rv = Common.checkParamsIsNotNull(data, "LOGIN", "PASSWORD", "REPEAT_PASSWORD", "USER_DATA");
            if (!rv.isOk())
                return DataMap.getInstance(rv);

            DataMap userData = (DataMap) data.get("USER_DATA");
            rv = Common.checkParamsIsNotNull(userData, "NAME", "LAST_NAME");
            if (!rv.isOk())
                return DataMap.getInstance(rv);

            String login = data.getString("LOGIN");
            String password = data.getString("PASSWORD");
            String repeatPassword = data.getString("REPEAT_PASSWORD");

            RegisterMenager menager = new RegisterMenager(db);
            if (!menager.insertNewUser(login, password, repeatPassword, userData))
                return DataMap.getInstance(menager.getReturnValue());

        }
        catch (Exception e)
        {
            return result;
        }

        result.setResultOk();
        return result;
    }


    public DataMap checkLoginIsTaken(DataMap data)
    {
        DataMap result = new DataMap();
        Database db = Database.getInstace();
        try
        {
            ReturnValue rv = Common.checkParamsIsNotNull(data, "LOGIN");
            if (!rv.isOk())
                return DataMap.getInstance(rv);

            String login = data.getString("LOGIN");

            RegisterMenager menager = new RegisterMenager(db);
            result = menager.checkLoginIsTaken(login);

        }
        catch (Exception e)
        {
            return result;
        }

        result.setResultOk();
        return result;
    }

}
