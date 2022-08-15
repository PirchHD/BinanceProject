package com.binanceui.Controllers;

import com.binanceapibackend.BinanceApiConnection;
import com.binanceui.PathToFxmlFile;
import com.binanceui.User;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
public class MainController{

    @FXML
    private Label lblMenu;
    @FXML
    private Label lblMenuClose;
    @FXML
    private AnchorPane slider;
    @FXML
    public AnchorPane primary;
    @FXML
    private Button btnLogin;
    @FXML
    private ImageView imLogin;

    public static boolean isLogged = false;


    /**
     * @Initialize
     * Initialize - przed wyświetleniem widoku ta metoda jest odpalana.
     */
    @FXML
    public void initialize() {
        setSlider();
        setChildrenInMain(SceneController.getParent(PathToFxmlFile.GENERAL_FXML));

        if(isLogged) {
            btnLogin.setText("Witam " + User.getInstance().getName());
            imLogin.setImage(null);
        }

    }

    public void setSlider(){
        slider.setTranslateX(-300);

        lblMenu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.5));
            slide.setNode(slider);

            slide.setToX(0);
            slide.play();

            slider.setTranslateX(-300);

            slide.setOnFinished((ActionEvent e)->{
                lblMenu.setVisible(false);
                lblMenuClose.setVisible(true);
            });
        });

        lblMenuClose.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.5));
            slide.setNode(slider);

            slide.setToX(-300);
            slide.play();

            slider.setTranslateX(0);

            slide.setOnFinished((ActionEvent e)->{
                lblMenu.setVisible(true);
                lblMenuClose.setVisible(false);
            });
        });
    }

    @FXML
    public void btnLoginOnAction(){
        if(isLogged)
            return;

        Stage stage = new Stage();
        SceneController.switchToScene(PathToFxmlFile.LOGIN_FXML, stage);
        closeAll();
    }

    @FXML
    public void btnBotOnAction() {
        setChildrenInMain(SceneController.getParent(PathToFxmlFile.BOT_FXML));

    }

    @FXML
    public void btnGeneralOnAction() {
        setChildrenInMain(SceneController.getParent(PathToFxmlFile.GENERAL_FXML));
    }

    @FXML
    public void btnSettingsOnAction() {
        if(!isLogged)
        {
            SceneController.creatInformationFxml("Przykro mi ale nie jesteś zalogowany :c");
            return;
        }


        setChildrenInMain(SceneController.getParent(PathToFxmlFile.SETTINGS_FXML));
    }
    @FXML
    public void btnAnalyzeOnAction() {
        setChildrenInMain(SceneController.getParent(PathToFxmlFile.ANALYZE_FXML));
    }

    @FXML
    public void btnExitOnAction() {
        primary.getChildren().removeAll();
        System.exit(0);
    }

    public void closeAll(){
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        stage.close();
    }


    private void setChildrenInMain(Parent parent){
        primary.getChildren().removeAll();
        primary.getChildren().setAll(parent);
    }


}
