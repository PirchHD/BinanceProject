package com.binanceapibackend.Database.entity;

import com.binanceapibackend.Database.Database;

public class DBTable {

    protected Database m_db;

    public DBTable (Database m_db)
    {
        this.m_db = m_db;
    }

    public Boolean insert (DBTable dbTable)
    {
        try
        {
            Integer id;

            id = (Integer) m_db.session.save(dbTable);
            m_db.entityManager.flush();

            if (id == null)
                return false;

        }
        catch (Exception ex)
        {
            return false;
        }

        return true;
    }

    public boolean update (DBTable dbTable)
    {
        try
        {
            m_db.session.update(dbTable);
            m_db.entityManager.flush();
        }
        catch (Exception e)
        {
            return false;
        }

        return true;
    }

    protected void setDB (Database m_db)
    {
        this.m_db = m_db;
    }
}
