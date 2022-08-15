package com.binanceapibackend.Database.Menager;

import com.binanceapibackend.DataMap;
import com.binanceapibackend.Database.Database;
import com.binanceapibackend.Database.entity.CommonLogin;
import com.binanceapibackend.Database.entity.CommonUserData;
import com.binanceapibackend.ReturnValue;

public class LoginManager extends ApplManager
{

    public LoginManager (Database db)
    {
        super(db);
    }

    public DataMap tryToSignIn (String login, String password)
    {
        DataMap result = new DataMap();
        try
        {
            openTransaction();

            RegisterMenager manager = new RegisterMenager(m_db);
            String hashPass = manager.hashingPassword(password);
            if (hashPass == null)
                return DataMap.getInstance(manager.getReturnValue());

            CommonLogin commonLogin = new CommonLogin(m_db);
            Long count = commonLogin.isExistDataByLogin(login);
            if (count < 1)
                return returnNull(ReturnValue.Common.NOT_ALLOWED_OPERATION, "Użytkownik o podanym loginie (" + login + ") nie istnieje");

            count = commonLogin.isExistByLoginAndPassword(login, hashPass);
            if (count < 1)
                return returnNull(ReturnValue.Common.NOT_ALLOWED_OPERATION, "Błędne hasło użytkownika");

            commonLogin = commonLogin.getUserData(login, hashPass);

            result.put("ID", commonLogin.getId());
            result.put("LOGIN", commonLogin.getLogin());

            CommonUserData commonUserData = new CommonUserData(m_db);
            commonUserData = commonUserData.loadDataByLoginId(commonLogin.getId());

            result.put("ID_USER_DATA", commonUserData.getId());
            result.put("NAME", commonUserData.getName());
            result.put("LAST_NAME", commonUserData.getLastName());
            result.put("EMAIL", commonUserData.getEmail());
            result.put("PHONE", commonUserData.getIphoneNumber());

        }
        catch (Exception ex)
        {
            return returnNull(ex);
        } finally
        {
            closeTransation();
        }
        result.setResultOk();
        return result;
    }
}
