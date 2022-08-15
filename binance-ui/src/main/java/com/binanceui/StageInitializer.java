package com.binanceui;

import com.binanceui.Controllers.SceneController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StageInitializer implements ApplicationListener<GeneralApplicationInitializer.StageReadyEvent> {

    private final ApplicationContext applicationContext;

    public StageInitializer(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(GeneralApplicationInitializer.StageReadyEvent event) {
        SceneController.switchToScene(PathToFxmlFile.MAIN_FXML);
    }
}
