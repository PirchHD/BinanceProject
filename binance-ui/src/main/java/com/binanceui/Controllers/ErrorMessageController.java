package com.binanceui.Controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class ErrorMessageController  {

    @FXML
    public Label code;
    @FXML
    public Label message;
    @FXML
    public JFXButton btnOk;

    public void setCodeText(Integer code) {
        this.code.setText(String.valueOf(code));
    }

    public void setMessageText(String message) {
        this.message.setText(message);
    }

    public void btnOkOnAction() {
        Stage stage = (Stage) btnOk.getScene().getWindow();
        stage.close();
    }

}
