package com.binanceui.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class InformationController {

    public Label message;

    @FXML
    private Button btnOk;

    public void btnOkOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) message.getScene().getWindow();
        stage.close();
    }

    public void setMessageText(String message) {
        this.message.setText(message);
    }

    public void setCodeText(int code) {
    }

}
