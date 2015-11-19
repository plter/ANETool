package com.plter.anetool;

import com.plter.anetool.controllers.MainViewController;
import com.plter.anetool.res.Strings;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle(Strings.TITLE);
        primaryStage.setScene(MainViewController.createScene());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
