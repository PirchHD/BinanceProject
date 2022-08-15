package com.binanceapibackend.Database.UIService;

import com.binanceapibackend.DataMap;
import com.binanceapibackend.Database.Common;
import com.binanceapibackend.Database.Database;
import com.binanceapibackend.Database.Menager.LoginManager;
import com.binanceapibackend.ReturnValue;

public class LoginService {

    /**
     * Metoda loguje się do systemu
     * <p>
     * Przyjmuję atrybuty:
     * LOGIN               - (String) login uzytkownika
     * PASSWORD            - (String) hasło użytkownika
     * <p>
     * Powinien zwrócić result (DataMap):
     * Gdy zadziała poprawnie :
     * RESULT = "OK"
     * <p>
     * Gdy zauważy błędy:
     * RESULT = "ERROR"
     **/
    public DataMap tryToSignIn(DataMap data) {
        DataMap result = new DataMap();
        Database db = Database.getInstace();
        try
        {
            ReturnValue rv = Common.checkParamsIsNotNull(data, "LOGIN", "PASSWORD");
            if (!rv.isOk())
                return DataMap.getInstance(rv);

            String login = data.getString("LOGIN");
            String password = data.getString("PASSWORD");

            LoginManager manager = new LoginManager(db);
            result = manager.tryToSignIn(login, password);
            if(result == null)
                return DataMap.getInstance(manager.getReturnValue());

        }
        catch (Exception e)
        {
            return result;
        }

        result.setResultOk();
        return result;
    }

}
