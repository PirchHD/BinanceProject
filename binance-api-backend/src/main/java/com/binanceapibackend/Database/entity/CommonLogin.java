package com.binanceapibackend.Database.entity;

import com.binanceapibackend.Database.Database;
import com.binanceapibackend.Database.Menager.RegisterMenager;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Setter
@Getter
@Table(name = "Common_Login", schema = "dbo", catalog = "Binance")
public class CommonLogin extends DBTable implements Serializable  {

    public static final String TABLE_NAME = "Common_Login";

    public CommonLogin(Database m_db) {
        super(m_db);
    }

    public CommonLogin() {
        super(null);
    }

    public static final class Collumns
    {
        public static String ID                     = "Id";
        public static String LOGIN                  = "Login";
        public static String PASSWORD               = "Password";
        public static String IS_VALID               = "IsValid";
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int id;
    private String login;
    private String password;
    private int isValid;

    @Basic
    @Column(name = "Login", nullable = false, length = 50)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "Password", nullable = false, length = 2147483647)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "IsValid", nullable = false)
    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommonLogin that = (CommonLogin) o;
        return id == that.id &&
                isValid == that.isValid &&
                Objects.equals(login, that.login) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, isValid);
    }

    public Long isExistDataByLogin(String login)
    {
        Long numberRecords = null;

        Query query = m_db.entityManager.createQuery("SELECT count(*)  FROM CommonLogin e  WHERE e.login = :login");
        query.setParameter("login", login);
        if(query.getSingleResult() != null)
            numberRecords = (Long) query.getSingleResult();

        return numberRecords;
    }


    public Long isExistByLoginAndPassword(String login, String password)
    {
        Long numberRecords = null;

        Query query = m_db.entityManager.createQuery("SELECT count(*) FROM CommonLogin e  WHERE e.login = :login AND e.password = :password");

        query.setParameter("login", login);
        query.setParameter("password", password);
        if(query.getSingleResult() != null)
            numberRecords = (Long) query.getSingleResult();

        return numberRecords;

    }

    public CommonLogin getUserData(String login, String hashPass){
        CommonLogin result;

        Query query = m_db.entityManager.createQuery("SELECT e " +
                "FROM CommonLogin e  " +
                "WHERE e.login = :login AND e.password = :password");

        query.setParameter("login", login);
        query.setParameter("password", hashPass);

        result = (CommonLogin) query.getSingleResult();

        return result;
    }

    public CommonLogin getById(int id) {
        CommonLogin result;

        Query query = m_db.entityManager.createQuery("SELECT e " +
                "FROM CommonLogin e  " +
                "WHERE e.id = :id");

        query.setParameter("id", id);

        result = (CommonLogin) query.getSingleResult();

        return result;
    }

}
