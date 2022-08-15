package com.binanceui.Controllers;

import com.binanceapibackend.DataMap;
import com.binanceapibackend.Database.UIService.RegisterService;
import com.binanceui.PathToFxmlFile;
import com.binanceui.ValidationCommon;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;


@Component
public class RegisterController{

    @FXML
    public JFXPasswordField txtPassword;
    @FXML
    public JFXPasswordField txtRepeatPassword;
    @FXML
    public JFXButton btnRegister;
    @FXML
    public JFXTextField txtLogin;
    @FXML
    public JFXTextField txtName;
    @FXML
    public JFXTextField txtLastName;
    @FXML
    public JFXTextField txtPhone;
    @FXML
    public JFXTextField txtEmail;

    /**
     * Flaga sprawdza czy okienko jest otwarte.
     */
    public boolean isOpen = false;

    /**
     * @Initialize Initialize - przed wyświetleniem widoku ta metoda jest odpalana.
     */
    @FXML
    public void initialize() {
        if (isOpen)
            return;

        isOpen = true;
    }

    @FXML
    public void btnRegisterOnAction() {
        String login = txtLogin.getText();
        String password = txtPassword.getText();
        String repeatPassword = txtRepeatPassword.getText();

        String name = txtName.getText();
        String lastName = txtLastName.getText();
        String phone = txtPhone.getText();
        String email = txtEmail.getText();

        if (ValidationCommon.isValueNullOrEmpty(login, txtLogin)
                || ValidationCommon.isValueNullOrEmpty(password, txtPassword)
                || ValidationCommon.isValueNullOrEmpty(repeatPassword, txtRepeatPassword)
                || ValidationCommon.isValueNullOrEmpty(name, txtName) || ValidationCommon.isValueNullOrEmpty(lastName, txtLastName))
            return;

        if (!password.equals(repeatPassword)) {
            SceneController.creatInformationFxml("Hasła powinny być takie same");
            return;
        }

        if (checkLoginIsTaken(login)) {
            SceneController.creatInformationFxml("Podany login już istnieje");
            return;
        }

        DataMap userData = new DataMap();
        userData.put("NAME", name);
        userData.put("LAST_NAME", lastName);
        userData.put("EMAIL", email);
        userData.put("PHONE_NUMBER", phone);

        DataMap data = new DataMap();
        data.put("LOGIN", login);
        data.put("PASSWORD", password);
        data.put("REPEAT_PASSWORD", repeatPassword);
        data.put("USER_DATA", userData);

        DataMap result = SceneController.ifInFunctionErrorCreateInformationMessage(new RegisterService().insertNewUser(data));
        if (!result.isResultOk())
            return;
        else
            SceneController.creatInformationFxml(0, "Udalo sie zarejestrowac");

        btnCancelOnAction();
        SceneController.switchToScene(PathToFxmlFile.LOGIN_FXML);
    }

    private boolean checkLoginIsTaken(String login) {
        Boolean isLoginTakean = true;

        DataMap data = new DataMap();
        data.put("LOGIN", login);

        DataMap result = SceneController.ifInFunctionErrorCreateInformationMessage(new RegisterService().checkLoginIsTaken(data));
        if (!result.isResultOk())
            return true;

        if (result.containsKey("IS_LOGIN_TAKEN"))
            isLoginTakean = result.getBoolean("IS_LOGIN_TAKEN");

        return isLoginTakean;
    }

    public void btnCancelOnAction() {
        isOpen = true;
        Stage stage = (Stage) txtLogin.getScene().getWindow();
        stage.close();
    }
}
