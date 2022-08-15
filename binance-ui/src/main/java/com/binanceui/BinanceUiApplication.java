package com.binanceui;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BinanceUiApplication {

	public static void main(String[] args) {
		Application.launch(GeneralApplicationInitializer.class, args);
	}

}
