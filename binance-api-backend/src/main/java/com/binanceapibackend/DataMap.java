package com.binanceapibackend;

import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public class DataMap extends HashMap<String, Object>
{
    private static final String OK_RESULT = "OK";
    private static final String ERROR_RESULT = "ERROR";

    /**
     * Utworzenie obiektu rezultatu HashMap na podstawie obiektu obsługi błędu.
     *
     * @param errorCode - obiekt obsługi błędu
     * @return Struktura HashMap
     */
    public static DataMap getInstance (ReturnValue errorCode)
    {
        DataMap result = new DataMap();

        if (errorCode.isOk())
            result.setResultOk();
        else if (errorCode.getErrorDetails() != null)
        {
            result.setError(errorCode.getCode());
            result.put(DataMapKey.RESULT, ERROR_RESULT);
            result.put(DataMapKey.ERROR_DETAILS, errorCode.getErrorDetails());
        }
        else
            result.setError(errorCode.getCode());

        return result;
    }

    public static DataMap getInstanceError (int code, String errorDetails)
    {
        DataMap result = new DataMap();
        result.put(DataMapKey.INFO, "");
        result.put(DataMapKey.ERROR_CODE, code);
        result.put(DataMapKey.ERROR_DETAILS, errorDetails);
        result.setResultError();

        return result;
    }

    /**
     * Sprawdzenie czy wartość dla danego klucza jest równa null
     *
     * @param key - klucz
     * @return true - gdy wartość jest równa null lub jej brak
     */
    public Boolean isNull (String key)
    {
        return !containsKey(key) || get(key) == null;
    }

    /**
     * Pobranie wartości typu String ze struktury HashMap.
     *
     * @param key - nazwa klucza
     * @return String dla danego klucza lub null w przypadku braku klucza w mapie
     */
    public String getString (String key)
    {
        if (containsKey(key))
            return (String) get(key);

        return null;
    }

    /**
     * Pobranie wartości typu Integer ze struktury HashMap.
     *
     * @param key - nazwa klucza
     * @return Integer dla danego klucza lub null w przypadku braku klucza w mapie
     */
    public Integer getInteger (String key)
    {
        if (containsKey(key))
            return (Integer) get(key);

        return null;
    }

    /**
     * Pobranie wartości typu Long ze struktury HashMap.
     *
     * @param key - nazwa klucza
     * @return Long dla danego klucza lub null w przypadku braku klucza w mapie
     */
    public Long getLong (String key)
    {
        if (containsKey(key))
            return (Long) get(key);

        return null;
    }

    /**
     * Pobranie wartości typu Double ze struktury HashMap.
     *
     * @param key - nazwa klucza
     * @return Double dla danego klucza lub null w przypadku braku klucza w mapie
     */
    public Double getDouble (String key)
    {
        if (containsKey(key))
            return Double.parseDouble(Objects.toString(get(key), "0.0"));

        return null;
    }

    /**
     * Pobranie wartości typu Date ze struktury HashMap.
     *
     * @param key - nazwa klucza
     * @return String dla danego klucza lub null w przypadku braku klucza w mapie
     */
    public Date getDate (String key)
    {
        if (containsKey(key))
            return (Date) get(key);

        return null;
    }

    /**
     * Pobranie wartości typu Boolean ze struktury HashMap.
     *
     * @param key - nazwa klucza
     * @return String dla danego klucza lub null w przypadku braku klucza w mapie
     */
    public Boolean getBoolean (String key)
    {
        if (containsKey(key))
            return (Boolean) get(key);

        return null;
    }

    /**
     * Sprawdzenie czy wartość ze struktury dla podanego klucza jest zgodna z podaną w wywołaniu metody.
     *
     * @param key   - klucz w strukturze
     * @param value - wartość oczekiwana
     * @return true - wartości zgodne, false - wartości nie zgodne
     */
    public Boolean checkValue (String key, Object value)
    {
        return Objects.equals(get(key), value);
    }

    /**
     * Ustawienie pozytywnego wyniku w strukturze DataMap
     */
    public void setResultOk ()
    {
        put(DataMapKey.RESULT, OK_RESULT);
    }

    /**
     * Ustawienie negatywnego wyniku w strukturze DataMap
     */
    public void setResultError ()
    {
        put(DataMapKey.RESULT, ERROR_RESULT);
    }

    /**
     * Sprawdzenie czy w strukturze DataMap jest zapisany pozytywny wynik.
     * Dla klucza RESULT sprawdzana jest wartość OK
     *
     * @return true - w strukturze zapisany jest wynik pozytywny,
     * false w przeciwnym wypadku
     */
    public Boolean isResultOk ()
    {
        return OK_RESULT.equals(get(DataMapKey.RESULT));
    }

    /**
     * Sprawdzenie czy w strukturze DataMap jest zapisany błąd.
     * Dla klucza RESULT sprawdzana jest wartość ERROR
     *
     * @return true - w strukturze zapisany jest błąd
     */
    public Boolean isResultError ()
    {
        return ERROR_RESULT.equals(get(DataMapKey.RESULT));
    }

    /**
     * Dodanie do struktury pola info.
     *
     * @param info - String dodatkowych informacji
     */
    public void setErrorInfo (String info)
    {
        put(DataMapKey.ERROR_INFO, info);
    }

    /**
     * Dodanie dodatkowego opisu do struktury DataMap
     *
     * @param details dodatkowe informacje
     */
    public void addDetails (String details)
    {
        put(DataMapKey.ERROR_DETAILS, Objects.toString(get(DataMapKey.ERROR_DETAILS), "") + details);
    }

    public void setError (int code)
    {
        put(DataMapKey.ERROR_CODE, code);
    }

    /**
     * Klasa opisująca klucze stosowane w DataMap
     */
    public class DataMapKey
    {
        public static final String RESULT = "RESULT";
        public static final String ERROR_MSG = "ERROR_MSG";
        public static final String ERROR_CODE = "ERROR_CODE";
        public static final String ERROR_INFO = "ERROR_INFO";
        public static final String EXCEPTION = "EXCEPTION";
        public static final String ERROR_DETAILS = "ERROR_DETAILS";
        public static final String INFO = "INFO";
    }

}
