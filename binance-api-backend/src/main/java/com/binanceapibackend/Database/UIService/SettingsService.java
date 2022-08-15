package com.binanceapibackend.Database.UIService;

import com.binanceapibackend.DataMap;
import com.binanceapibackend.Database.Common;
import com.binanceapibackend.Database.Database;
import com.binanceapibackend.Database.Menager.SettingsManager;
import com.binanceapibackend.ReturnValue;

public class SettingsService {


    /**
     * @Attributes
     * data(DataMap) - DataMap z wartościami:
     *      USERNAME
     *      NAME
     *      LASTNAME
     *      LOGIN_ID
     *
     * @Description
     * Metoda updejtuje tabele Common_UserData
     *
     * @return result
     * Result.isResultOk = true gdy wszystko zadziałało
     * Result.isResultOk = false gdy metoda napotkała błąd i zwraca opis tego błędu
     */
    public DataMap updateCommonUserData(DataMap data)
    {
        DataMap result = new DataMap();
        Database db = Database.getInstace();
        try
        {
            ReturnValue rv = Common.checkParamsIsNotNull(data, "USERNAME", "NAME", "LASTNAME", "LOGIN_ID");
            if (!rv.isOk())
                return DataMap.getInstance(rv);

            Integer loginId = data.getInteger("LOGIN_ID");
            String username = data.getString("USERNAME");
            String name = data.getString("NAME");
            String lastName = data.getString("LASTNAME");
            String email = data.getString("EMAIL");
            String phone = data.getString("PHONE");

            SettingsManager manager = new SettingsManager(db);
             if(!manager.updateCommonUserData(loginId, username, name, lastName, email, phone))
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
