package com.binanceapibackend.Database.entity;

import com.binanceapibackend.DataMap;
import com.binanceapibackend.Database.Database;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "Common_UserData", schema = "dbo", catalog = "Binance")
public class CommonUserData extends DBTable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "LoginId", nullable = false, referencedColumnName = "id")
    private CommonLogin loginId;

    @Basic
    @Column(name = "Name", nullable = false, length = 2147483647)
    private String name;

    @Basic
    @Column(name = "LastName", nullable = false, length = 2147483647)
    private String lastName;

    @Basic
    @Column(name = "IphoneNumber", length = 2147483647)
    private String iphoneNumber;

    @Basic
    @Column(name = "Email", length = 2147483647)
    private String email;

    public CommonUserData(Database m_db) {
        super(m_db);
    }

    public CommonUserData() {
        super(null);
    }

    public CommonUserData getUserDataByLoginId(int id) {
        CommonUserData result;

        CommonLogin cl = new CommonLogin(m_db);
        cl = cl.getById(id);

        Query query = m_db.entityManager.createQuery("SELECT e " +
                "FROM CommonUserData e  " +
                "WHERE e.loginId = :loginId");

        query.setParameter("loginId", cl);
        result = (CommonUserData) query.getSingleResult();
        result.setDB(m_db);

        return result;
    }

    public CommonUserData loadDataByLoginId(int loginId)
    {
        CommonUserData result = null;

        CommonLogin cl = new CommonLogin(m_db);
        cl = cl.getById(loginId);

        Query query = m_db.entityManager.createQuery("SELECT e " +
                "FROM CommonUserData e  " +
                "WHERE e.loginId = :loginId");

        query.setParameter("loginId", cl);
        result = (CommonUserData) query.getSingleResult();

        return result;
    }

}
