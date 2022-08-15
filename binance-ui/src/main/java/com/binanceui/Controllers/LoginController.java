package com.binanceui.Controllers;

import com.binanceapibackend.DataMap;
import com.binanceapibackend.Database.UIService.LoginService;
import com.binanceapibackend.Database.entity.CommonLogin;
import com.binanceapibackend.Database.entity.CommonUserData;
import com.binanceui.PathToFxmlFile;
import com.binanceui.User;
import com.binanceui.ValidationCommon;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class LoginController {

    @FXML
    public Button btnCancel;
    @FXML
    public AnchorPane anarchyPane;
    @FXML
    public JFXPasswordField txtPassword;
    @FXML
    public JFXTextField txtLogin;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnRegister;
    @FXML
    private Text txtMessage;

    /**
     * @Initialize Initialize - przed wyświetleniem widoku ta metoda jest odpalana.
     */
    @FXML
    public void initialize() {
    }

    /**
     * Listener dla przycisku btnCancel. Zamyka on cały program
     */
    @FXML
    public void btnCancelOnAction() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    /**
     * Listener dla przycisku btnLogin. Odpala proces logowania się
     */
    @FXML
    public void btnLoginOnAction() {
        String login = txtLogin.getText();
        String password = txtPassword.getText();

        if (ValidationCommon.isValueNullOrEmpty(login, txtLogin) || ValidationCommon.isValueNullOrEmpty(password, txtPassword))
            return;

        if (!tryToLoginIn(login, password))
            return;

        Stage stage = (Stage) anarchyPane.getScene().getWindow();
        stage.close();
    }

    /**
     * @Attributes
     * login(String) - Login, którym użytkownik próbuje sie zalogować
     * password(String) - Hasło, którym użytkownik próbuje sie zalogować
     *
     * @Description
     * Metoda odpala metoda z backendu tryToSignIn, która
     * sprawdza czy dany użtkownik istnieje oraz czy wpisał dobre hasło.
     * W razie problemów jest wyświetlane okienko z informacją
     *
     * @return true/false - czy się udało ?
     */
    private boolean tryToLoginIn(String login, String password){
        DataMap data = new DataMap();
        data.put("LOGIN", login);
        data.put("PASSWORD", password);

        DataMap result = SceneController.ifInFunctionErrorCreateInformationMessage(new LoginService().tryToSignIn(data));
        if (result.isResultOk())
        {
            User.createUserInstance(result);
            SceneController.switchToScene(PathToFxmlFile.MAIN_FXML);
            SceneController.creatInformationFxml("Udało sie zalogowac. Czesc " + result.getString("LOGIN"));
            return true;
        }

        return false;
    }


    /**
     * Listener dla przycisku btnRegister. Odpala on formularz do logowan (RegisterController)
     */
    @FXML
    public void btnRegisterOnAction() {
        SceneController.switchToScene(PathToFxmlFile.REGISTER_FXML);
        btnCancelOnAction();
    }

}
