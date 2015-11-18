package com.plter.anetool;

import com.plter.anetool.res.Strings;
import com.plter.anetool.views.ViewTool;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = ViewTool.loadView("MainView.fxml");
        primaryStage.setTitle(Strings.TITLE);
        primaryStage.setScene(new Scene(root, 1000, 650));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
