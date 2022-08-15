package com.binanceapibackend;

import java.util.ArrayList;
import java.util.HashMap;

public class ReturnValue {

    static
    {
        m_codeDescription = new HashMap<Integer, String>();
        loadDescription();
    }

    /** Kod błędu zapisany w klasie */
    private int m_errorCode;
    /** Tekst błędu */
    private String m_errorText;
    /** Dodatkowy opis błędu/komunikatu */
    private String m_errorDetails;
    /** Lista opisów poszczególnych błędów */
    protected static HashMap<Integer, String> m_codeDescription;

    /**
     * Uzupelnienie informacji o błędzie/komunikacie na podstawie przekazanego obiektu obsługi błędu/komunikatu.
     * @param returnValue obiekt obsługi błędu/komunikatu
     */
    public void setReturnValue(ReturnValue returnValue)
    {
        m_errorText     = returnValue.getErrorText();
        m_errorDetails  = returnValue.getErrorDetails();
        m_errorCode     = returnValue.getCode();
    }

    /**
     * Zapisanie błędu wraz z dodatkowym opisem.
     * @param code kod błędu
     * @param details dodatkowy opis błędu/komunikatu
     */
    public void setError(int code, String details)
    {
        m_errorCode = code;
        m_errorDetails = details;
    }

    /** Dodatkowy opis błędu/komunikatu */
    public String getErrorDetails()
    {
        return m_errorDetails;
    }

    /** Tekst błędu */
    public String getErrorText()
    {
        if (m_errorText == null)
            return null;

        String text = m_errorText;

        return text;
    }

    /**
     * Załadowanie opisów dla poszczególnych kodów błędów.
     */
    public static void loadDescription()
    {
        // Common - 0
        m_codeDescription.put(Common.EXCEPTION, "Wyjątek");
        m_codeDescription.put(Common.WRONG_PARAMETER, "Błędne wywołanie metody");
        m_codeDescription.put(Common.PARAMETER_NOT_FOUND, "Brak wymaganego parametru");
    }

    /**
     * Pobranie opisu dla danego kodu (surowego). Takiego jaki został załadowany.
     * Tzn. Może zawierać ciągi {0}, {1}, ... które mogą zostać zastąpione danymi
     * @param id - identyfikator błędu
     * @return Tekst błędu
     */
    public static String getDescription(int id)
    {
        return m_codeDescription.get(id);
    }

    /**
     * Pobranie kodu błędu zapisanego w klasie
     * @return Kod błędu (id)
     */
    public int getCode()
    {
        return m_errorCode;
    }


    /**
     * Metoda ustawia kod błedu na wartość 0 (wynik prawidłowy).
     */
    public void setOk()
    {
        m_errorCode = 0;
    }

    /**
     * Sprawdzenie czy kod błędu jest równy 0, Błąd nie występuje
     * @return true - błąd nie występuje, false - błąd występuje
     */
    public boolean isOk()
    {
        return (m_errorCode == 0);
    }
    /**
     * Pobranie instancji klasy z ustawionym kodem błędu na 0 - brak błędu
     * @return Obiekt klasy
     */
    static public ReturnValue getInstanceOk(){
        ReturnValue errorCode = new ReturnValue();
        errorCode.m_errorCode = 0;
        return errorCode;
    }

    /**
     * Pobranie instancji klasy z ustawionym kodem błędu na 0 - brak błędu  i lista dodatkowych informacji
     * @param dataInformation obiekt dodatkowych danych
     * @return Obiekt klasy
     */
    static public ReturnValue getInstanceOk(HashMap<String, Object> dataInformation) {
        return getInstance(0, dataInformation);
    }


    /**
     * Pobranie instancji klasy z ustawionym kodem błędu i listą parametrów.
     * @param error - kod błędu
     * @param param - lista parametrów
     * @return Obiekt klasy
     */
    static public ReturnValue getInstance(int error, Object ...param)
    {
        ReturnValue errorCode = new ReturnValue();

        return errorCode;
    }


    /**
     * Pobranie instancji klasy z ustawionym kodem błędu i listą parametrów oraz dodatkowych informacji
     * @param error - kod błędu
     * @param dataInformation obiekt dodatkowych danych
     * @param param - lista parametrów
     *
     * @return Obiekt klasy returnValue
     */
    static public ReturnValue getInstance(int error, HashMap<String, Object> dataInformation, Object ...param)
    {
        ReturnValue errorCode = new ReturnValue();


        return errorCode;
    }

    /**
     * Pobranie instancji klasy z ustawionym kodem błędu i lista dodatkowych informacji
     * @param error kod bledu
     * @param dataInformation obiekt HashMapy dodatkowych informacji
     * @return
     */
    public static ReturnValue getInstance(int error, HashMap<String, Object> dataInformation)
    {
        ReturnValue errorCode = new ReturnValue();

        return errorCode;
    }

    /**
     * Pobranie instancji klasy z ustawionym tekstem błędu i listą parametrów.
     * @param error - kod błędu
     * @param details - opis błędu
     * @param info - dodatkowa techniczna informacja
     * @param param - lista parametrów
     * @return Obiekt klasy
     */
    public static ReturnValue getInstance(int error, String details, String info, Object ...param)
    {
        ReturnValue returnValue = new ReturnValue();
        returnValue.m_errorCode = error;
        returnValue.m_errorText = getDescription(error);
        if (returnValue.m_errorText == null)
            returnValue.m_errorText = details;
        returnValue.m_errorDetails = details;

        return returnValue;
    }

    /**
     * Pobranie instancji klasy z ustawionym tekstem błędu i listą parametrów.
     * @param text - kod błędu
     * @param param - lista parametrów
     * @return Obiekt klasy
     */
    static public ReturnValue getInstance(String text, String details, String info, Object ...param)
    {
        ReturnValue returnValue = new ReturnValue();
        returnValue.m_errorCode = -1;
        returnValue.m_errorText = text;
        returnValue.m_errorDetails = details;

        return returnValue;
    }

    /**
     * Wspólne kody błędów
     */
    public class Common
    {
        public static final int UNKNOWN = -1;
        public static final int EXCEPTION = -1;
        public static final int WRONG_PARAMETER = -2;
        public static final int WARN = -3;
        public static final int QUESTION = -4;
        public static final int NOT_ALLOWED_OPERATION = -5;
        public static final int PARAMETER_NOT_FOUND = -6;
    }

    /**
     * Wspólne kody błędów związane z DB
     */
    public class DB
    {
        public static final int UNKNOWN = -1;
        public static final int INSERT = -100;
        public static final int UPDATE = -101;
        public static final int DELETE = -102;
        public static final int RECORD_NO_EXISTS = -103;
    }

}
