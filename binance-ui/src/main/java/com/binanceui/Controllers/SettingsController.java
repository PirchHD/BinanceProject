package com.binanceui.Controllers;

import com.binanceapibackend.DataMap;
import com.binanceapibackend.Database.UIService.SettingsService;
import com.binanceui.User;
import com.binanceui.ValidationCommon;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import org.springframework.stereotype.Component;

@Component
public class SettingsController
{

    @FXML
    public JFXTextField txtFieldEmail;
    @FXML
    public JFXTextField txtFieldPhone;
    @FXML
    public JFXTextField txtFieldUsername;
    @FXML
    public JFXTextField txtFieldName;
    @FXML
    public JFXTextField txtFieldLastName;
    @FXML
    public JFXButton btnUpdate;

    /**
     * @Initialize Initialize - przed wyświetleniem widoku ta metoda jest odpalana.
     */
    @FXML
    public void initialize ()
    {
        setTestField();
    }

    private void setTestField ()
    {
        User user = User.getInstance();

        if (user.getUsername() != null)
            txtFieldUsername.setText(user.getUsername());
        if (user.getName() != null)
            txtFieldName.setText(user.getName());
        if (user.getLastName() != null)
            txtFieldLastName.setText(user.getLastName());

        if (user.getEmail() != null)
            txtFieldEmail.setText(user.getEmail());
        else
            txtFieldEmail.setText("****@gmail.com");

        if (user.getPhone() != null)
            txtFieldPhone.setText(user.getPhone());
        else
            txtFieldPhone.setText("000-000-000");

    }

    public void btnUpdateOnAction ()
    {
        if (ValidationCommon.isValueNullOrEmpty(txtFieldUsername.getText(), txtFieldUsername)
                || ValidationCommon.isValueNullOrEmpty(txtFieldName.getText(), txtFieldName)
                || ValidationCommon.isValueNullOrEmpty(txtFieldLastName.getText(), txtFieldLastName))
            return;

        DataMap userData = new DataMap();
        userData.put("LOGIN_ID", User.getInstance().getId());
        userData.put("USERNAME", txtFieldUsername.getText());
        userData.put("NAME", txtFieldName.getText());
        userData.put("LASTNAME", txtFieldLastName.getText());
        userData.put("EMAIL", txtFieldEmail.getText());
        userData.put("PHONE_NUMBER", txtFieldPhone.getText());

        DataMap result = SceneController.ifInFunctionErrorCreateInformationMessage(new SettingsService().updateCommonUserData(userData));
        if (result.isResultOk())
            SceneController.creatInformationFxml("Udało się updatować dane :)");

    }
}
