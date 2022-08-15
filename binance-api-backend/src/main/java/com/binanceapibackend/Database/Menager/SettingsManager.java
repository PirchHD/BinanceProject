package com.binanceapibackend.Database.Menager;

import com.binanceapibackend.Database.Database;
import com.binanceapibackend.Database.entity.CommonUserData;
import com.binanceapibackend.ReturnValue;
import com.mysql.jdbc.StringUtils;

public class SettingsManager extends ApplManager
{

    public SettingsManager (Database db)
    {
        super(db);
    }

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
     * @return true/false - Czy się udało
     */
    public Boolean updateCommonUserData (Integer loginId, String username, String name, String lastName, String email, String phone)
    {
        try
        {
            openTransaction();

            RegisterMenager manager = new RegisterMenager(m_db);

            if (!StringUtils.isNullOrEmpty(phone) && !manager.checkNumber(phone))
                return returnFalse(ReturnValue.Common.NOT_ALLOWED_OPERATION, "Bledny numer telefonu");

            if (!StringUtils.isNullOrEmpty(email) && !manager.isValidEmailAddress(email))
                return returnFalse(ReturnValue.Common.NOT_ALLOWED_OPERATION, "Bledny email");

            CommonUserData commonUserData = new CommonUserData(m_db);
            commonUserData = commonUserData.getUserDataByLoginId(loginId);
            if (commonUserData == null)
                return returnFalse(ReturnValue.DB.RECORD_NO_EXISTS, "Rekord o LoginId " + loginId + "Nie istnieje");;

            commonUserData.setName(name);
            commonUserData.setLastName(lastName);
            commonUserData.setEmail(email);
            commonUserData.setIphoneNumber(phone);

            if (!commonUserData.update(commonUserData))
                return returnFalse(ReturnValue.DB.UPDATE, "Nie udało się updatować podane wartości");

        }
        catch (Exception e)
        {
            return returnFalse(e);
        } finally
        {
            closeTransation();
        }

        return true;
    }

}
