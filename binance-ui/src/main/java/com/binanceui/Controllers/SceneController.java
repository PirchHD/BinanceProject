package com.binanceui.Controllers;

import com.binanceapibackend.DataMap;
import com.binanceui.PathToFxmlFile;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class SceneController
{

    public final static String ERROR_FXML = "/FxmlError.fxml";

    private String fxmlPath;

    public SceneController(String fxmlPath, Stage stage, Scene scene, Parent parent)
    {
        this.fxmlPath = fxmlPath;
        this.stage = stage;
        this.scene = scene;
        this.parent = parent;
    }

    private Stage stage;
    private Scene scene;
    private Parent parent;

    public SceneController(String fxmlPath) throws IOException
    {
        this.fxmlPath = fxmlPath;
        Parent root = FXMLLoader.load(SceneController.class.getResource(fxmlPath));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
    }

    /*
    public static void creatErrorMessage(DataMap errorDataMap){
        try
        {
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource(PathToFxmlFile.ERROR_FXML));
            Parent parent = loader.load();
            ErrorMessageController controller = loader.getController();

            if(errorDataMap != null)
            {
                Integer code = errorDataMap.getInteger("ERROR_CODE");
                String errorDetails = errorDataMap.getString("ERROR_DETAILS");
                controller.setCodeText(code);
                controller.setMessageText(errorDetails);
            }
            else
            {
                controller.setCodeText(0);
                controller.setMessageText("Nie rozpoznany błąd");
            }

            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(parent));
            stage.show();
        }
        catch (Exception e)
        {
            System.exit(-1);
        }
    }

*/

    public static void creatErrorMessage(Exception e)
    {

    }

    public static DataMap ifInFunctionErrorCreateInformationMessage(DataMap dataMap)
    {
        if(dataMap.get("RESULT") == null || dataMap.get("RESULT").equals("ERROR") || dataMap == null)
            creatInformationFxml(dataMap);

        return dataMap;
    }

    public static void creatInformationFxml (DataMap infoDataMap)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource(PathToFxmlFile.INFO_FXML));
            Parent parent = loader.load();
            InformationController controller = loader.getController();

            if(infoDataMap != null)
            {
                Integer code = infoDataMap.getInteger("ERROR_CODE");
                String errorDetails = infoDataMap.getString("ERROR_DETAILS");
                controller.setCodeText(code);
                controller.setMessageText(errorDetails);
            }
            else
            {
                controller.setCodeText(-1);
                controller.setMessageText("Nie rozpoznany błąd");
            }

            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(parent));
            stage.show();

        }
        catch (Exception e)
        {
            System.exit(-1);
        }
    }

    public static void creatInformationFxml (String message)
    {
        creatInformationFxml(0, message);
    }

    public static void creatInformationFxml (int code, String message){
        try
        {
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource(PathToFxmlFile.INFO_FXML));
            Parent parent = loader.load();

            InformationController controller = loader.getController();
            controller.setCodeText(code);
            controller.setMessageText(message);

            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(parent));
            stage.show();
        }
        catch (Exception e)
        {
            System.exit(-1);
        }
    }

    public static void reloadScene(String pathToFXML)
    {
        try
        {
            FXMLLoader fxmlLoader =  new FXMLLoader(SceneController.class.getResource(pathToFXML));
            fxmlLoader.load();
        }
        catch (Exception e) {
            creatErrorMessage(e);
        }
    }

    public static void switchToScene(String pathToFXML)
    {
        try
        {
            Parent root = FXMLLoader.load(SceneController.class.getResource(pathToFXML));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (Exception e)
        {
            creatErrorMessage(e);
        }
    }

    public static void switchToScene(String pathToFXML, Stage setupStage) {
        try
        {
            Parent root = FXMLLoader.load(SceneController.class.getResource(pathToFXML));
            setupStage.setScene(new Scene(root));
            setupStage.show();
        }
        catch (Exception e)
        {
            creatErrorMessage(e);
        }
    }

    public static Parent getParent(String pathToFXML) {
        Parent root = null;

        try
        {
            root = FXMLLoader.load(SceneController.class.getResource(pathToFXML));
        }
        catch (Exception e)
        {
            creatErrorMessage(e);
        }

        return root;
    }



    public static Scene getScene(String pathToFXML)
    {
        Parent root = null;
        try
        {
            root = FXMLLoader.load(SceneController.class.getResource(pathToFXML));
        }
        catch (Exception e)
        {
            creatErrorMessage(e);
        }

        return new Scene(root);
    }

    public String getFxmlPath() {
        return fxmlPath;
    }

    public Stage getStage() {
        return stage;
    }

    public Scene getScene() {
        return scene;
    }

    public Parent getParent() {
        return parent;
    }


}
