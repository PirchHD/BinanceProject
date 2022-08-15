package com.binanceapibackend.Database.Menager;

import com.binanceapibackend.DataMap;
import com.binanceapibackend.Database.Database;
import com.binanceapibackend.Database.entity.CommonLogin;
import com.binanceapibackend.Database.entity.CommonUserData;
import com.binanceapibackend.ReturnValue;
import com.mysql.jdbc.StringUtils;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegisterMenager extends ApplManager
{

    public RegisterMenager (Database db)
    {
        super(db);
    }

    public Boolean insertNewUser (String login, String password, String repeatPassword, DataMap userData)
    {
        try
        {
            openTransaction();

            if (!password.equals(repeatPassword))
                return returnFalse(ReturnValue.Common.NOT_ALLOWED_OPERATION, "Hasła się nie zgadzją");

            String hashPassword = hashingPassword(password);
            if (hashPassword == null)
                return returnFalse(ReturnValue.Common.EXCEPTION, "Problem z hashowaniem hassla");

            String phone = userData.getString("PHONE_NUMBER");
            if (!StringUtils.isNullOrEmpty(phone) && !checkNumber(phone))
                return returnFalse(ReturnValue.Common.NOT_ALLOWED_OPERATION, "Bledny numer telefonu");

            String email = userData.getString("EMAIL");
            if (!StringUtils.isNullOrEmpty(email) && !isValidEmailAddress(email))
                return returnFalse(ReturnValue.Common.NOT_ALLOWED_OPERATION, "Bledny email");

            CommonLogin commonLogin = new CommonLogin(m_db);
            commonLogin.setLogin(login);
            commonLogin.setPassword(hashPassword);
            commonLogin.setIsValid(1);

            if (!commonLogin.insert(commonLogin))
                return returnFalse(ReturnValue.DB.INSERT, "Nie udało się dodać użytkownika " + login);

            CommonUserData commonUserData = new CommonUserData(m_db);
            commonUserData.setLoginId(commonLogin);
            commonUserData.setName(userData.getString("NAME"));
            commonUserData.setLastName(userData.getString("LAST_NAME"));
            commonUserData.setEmail(userData.getString("EMAIL"));
            commonUserData.setIphoneNumber(userData.getString("PHONE_NUMBER"));

            if (!commonUserData.insert(commonUserData))
                return returnFalse(ReturnValue.DB.INSERT, "Nie udało się dodać dane użytkownika " + login);

        }
        catch (Exception ex)
        {
            return returnFalse(ex);
        } finally
        {
            closeTransation();
        }

        return true;
    }

    public String hashingPassword (String passwordToHash)
    {
        String generatedPassword;
        try
        {
            if (passwordToHash == null)
                return null;

            if (passwordToHash.isBlank() || passwordToHash.isEmpty())
                return null;

            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(passwordToHash.getBytes());

            byte[] bytes = md.digest();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();

        }
        catch (NoSuchAlgorithmException e)
        {
            return null;
        }

        return generatedPassword;
    }

    public boolean checkNumber (String number)
    {

        if (number == null || number.isEmpty() || number.isBlank())
            return false;

        if (number.length() != 9)
            return false;

        try
        {
            Integer.parseInt(number);
        }
        catch (NumberFormatException e)
        {
            return false;
        }

        return true;
    }

    public boolean isValidEmailAddress (String email)
    {
        boolean result = true;
        try
        {
            if (email == null)
                return false;

            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        }
        catch (AddressException ex)
        {
            result = false;
        }

        return result;
    }


    public DataMap checkLoginIsTaken (String login)
    {
        DataMap result = new DataMap();
        result.put("IS_LOGIN_TAKEN", true);

        try
        {
            openTransaction();
            CommonLogin commonLogin = new CommonLogin(m_db);
            Long count = commonLogin.isExistDataByLogin(login);

            if (count == 0)
                result.put("IS_LOGIN_TAKEN", false);
            else
                result.put("IS_LOGIN_TAKEN", true);

        }
        catch (Exception ex)
        {
            return returnNull(ex);
        } finally
        {
            closeTransation();
        }

        return result;
    }

}
