package com.binanceapibackend.Database;

import com.binanceapibackend.DataMap;
import com.binanceapibackend.ReturnValue;

public class Common
{

    /**
     * Sprawdzanie czy parametry przekazane w obiekcie DataMap nie są przypadkiem null
     *
     * @param paramData - mapa parametrów
     * @param args - lista nazw parametrów, które nie mogą być null
     * @return obiekt klasy obsługi błędów
     */
    public static ReturnValue checkParamsIsNotNull(DataMap paramData, String... args)
    {
        for (String paramName : args)
        {
            if (paramData.get(paramName) == null)
                return ReturnValue.getInstance(ReturnValue.Common.WARN, "Parametr " + paramName +  " nie powinien byc null", null);
        }

        return ReturnValue.getInstanceOk();
    }


}
