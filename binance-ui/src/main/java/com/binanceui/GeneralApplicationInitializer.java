package com.binanceui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

public class GeneralApplicationInitializer extends Application {

    private ConfigurableApplicationContext configurableApplicationContext;

    @Override
    public void init() {
        configurableApplicationContext = new SpringApplicationBuilder(BinanceUiApplication.class).run();
    }

    @Override
    public void stop() {
        configurableApplicationContext.stop();
        Platform.exit();
        System.exit(1);
    }

    @Override
    public void start(Stage primaryStage) {
        configurableApplicationContext.publishEvent(new StageReadyEvent(primaryStage));
    }

    static class StageReadyEvent extends ApplicationEvent {
        public StageReadyEvent(Stage primaryStage) {
            super(primaryStage);
        }

        public Stage getStage() {
            return (Stage) getSource();
        }

    }
}
