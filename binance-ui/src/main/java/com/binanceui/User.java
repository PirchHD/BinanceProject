package com.binanceui;

import com.binanceapibackend.DataMap;
import com.binanceui.Controllers.MainController;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User
{

    public static User user;

    /**
     * Id do Common_Login
     */
    private Integer id;
    /**
     * Id do Common_UserData
     */
    private Integer id_userData;
    /**
     * Login użytkownika
     */
    private String username;
    /**
     * Imie użytkownika
     */
    private String name;
    /**
     * Nazwisko użytkownika
     */
    private String lastName;
    /**
     * telefon użytkownika
     */
    private String phone;
    /**
     * email użytkownika
     */
    private String email;

    public static User getInstance ()
    {
        if (user == null)
        {
            user = new User();
        }

        return user;
    }

    public static void createUserInstance (DataMap userData)
    {
        User user = User.getInstance();

        user.setId(userData.getInteger("ID"));
        user.setUsername(userData.getString("LOGIN"));
        user.setName(userData.getString("NAME"));
        user.setLastName(userData.getString("LAST_NAME"));
        user.setEmail(userData.getString("EMAIL"));
        user.setPhone(userData.getString("PHONE"));

        MainController.isLogged = true;
    }

}
