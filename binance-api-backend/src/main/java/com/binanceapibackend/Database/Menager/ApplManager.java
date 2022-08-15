package com.binanceapibackend.Database.Menager;

import com.binanceapibackend.Database.Database;
import com.binanceapibackend.ReturnValue;

public class ApplManager {

    /** Obiekt Database który posiada połączenie do bazy danych/ transakcje i itp*/
    Database m_db;

    /** Obiekt ReturnValue który zwraca kod błędu jeśli takowy wystąpił*/
    ReturnValue m_returnValue;

    public ApplManager(Database db)
    {
        if (db == null)
            m_db = Database.getInstace();
        else
           m_db = db;

    }

    public void openTransaction()
    {
        m_db.transaction.begin();
    }

    public void closeTransation()
    {
        m_db.transaction.commit();

        if (m_db.transaction.isActive())
            m_db.transaction.rollback();

        m_db.entityManager.close();
        m_db.entityManagerFactory.close();
    }

    /**
     * Metoda zwraca obiekt reprezentujący zapisany błąd.
     *
     * @return obiekt zarejestrowanego błędu
     */
    public ReturnValue getReturnValue()
    {
        return m_returnValue;
    }

    /**
     * Ustawienie obiektu klasy błędu.
     *
     * @param returnValue - obiekt klasy błędu
     */
    public void setReturnValue(ReturnValue returnValue)
    {
        m_returnValue = returnValue;
    }

    /**
     * Metoda ustawia kod błedu na wartość 0 (wynik prawidłowy).
     */
    public void setOk()
    {
        m_returnValue = ReturnValue.getInstanceOk();
    }

    /**
     * Zwrócenie błędu. Metoda zapisuje obiekt błędu i zwraca false
     *
     * @param rv - obiekt błedu;
     * @return false
     */
    public boolean returnFalse(ReturnValue rv)
    {
        setReturnValue(rv);
        return false;
    }

    /**
     * Zwrócenie błędu. Metoda zapisuje obiekt błędu i zwraca null
     *
     * @param rv - obiekt błedu;
     * @return null
     */
    public <Any> Any returnNull(ReturnValue rv)
    {
        setReturnValue(rv);
        return null;
    }

    /**
     * Zwrócenie błędu. Metoda wykonuje rollback, zapisuje obiekt błędu i zwraca false
     *
     * @param rv - obiekt błedu;
     * @return false
     */
    public boolean returnFalseAfterRollback(ReturnValue rv)
    {
        //rollback();
        return returnFalse(rv);
    }

    /**
     * Zwrócenie błędu. Metoda wykonuje rollback, zapisuje obiekt błędu i zwraca null
     *
     * @param rv - obiekt błedu;
     * @return null
     */
    public <Any> Any returnNullAfterRollback(ReturnValue rv)
    {
        //rollback();
        return returnNull(rv);
    }


    /**
     * Zwrócenie błędu. Metoda zapisuje wyjątek i zwraca null
     *
     * @param ex - wyjątek
     * @return null
     */
    public <Any> Any returnNull(Exception ex)
    {
        //setException(ex);
        return null;
    }

    /**
     * Zwrócenie błędu. Metoda zapisuje wyjątek i zwraca false
     *
     * @param ex - wyjątek
     * @return null
     */
    public boolean returnFalse(Exception ex)
    {
        //setException(ex);
        return false;
    }

    /**
     * Zwrócenie błędu. Metoda zapisuje obiekt błędu i zwraca false
     *
     * @param error - kod błędu (opis)
     * @param details - opis błędu
     * @return false
     */
    public boolean returnFalse(String error, String details)
    {
        setError(error, details);
        return false;
    }

    /**
     * Zwrócenie błędu. Metoda zapisuje obiekt błędu i zwraca null
     *
     * @param error - kod błędu (opis)
     * @param details - opis błędu
     * @return null
     */
    public <Any> Any returnNull(String error, String details)
    {
        setError(error, details);
        return null;
    }

    /**
     * Zwrócenie błędu. Metoda zapisuje obiekt błędu i zwraca false
     *
     * @param error - kod błędu
     * @param details - opis błędu
     * @return false
     */
    public boolean returnFalse(int error, String details)
    {
        setError(error, details);
        return false;
    }

    /**
     * Zwrócenie błędu. Metoda zapisuje obiekt błędu i zwraca null
     *
     * @param error - kod błędu
     * @param details - opis błędu
     * @return null
     */
    public <Any> Any returnNull(int error, String details)
    {
        setError(error, details);
        return null;
    }


    /**
     * Metoda zapisująca kod błędu ze szczegółową informacją (metoda LEGACY)
     */
    public void setError(Integer errorMessage, String details)
    {
        if (m_returnValue == null)
            m_returnValue = new ReturnValue();

        m_returnValue.setError(errorMessage, details);
    }

    /**
     * Metoda zapisująca kod błędu ze szczegółową informacją (metoda LEGACY)
     */
    public void setError(String errorMessage, String details)
    {
        m_returnValue.setError(ReturnValue.Common.UNKNOWN, details);

    }


}
