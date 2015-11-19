package com.plter.anetool.controllers;

import com.plter.anetool.views.ViewTool;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable{


    public Button btnBrowseForJdkPath;
    public Button btnBrowseForSwcPath;
    public VBox containerAndroidPlatform;
    public CheckBox cbSupportAndroid;
    public VBox containeriOSPlatform;
    public CheckBox cbSupportiOS;
    public Button btnBrowseForiOSAPath;
    public VBox containerWindowsPlatform;
    public Button btnBrowseForWindowsDllPath;
    public VBox containerMacPlatform;
    public Button btnBrowseForMacAPath;
    public CheckBox cbSupportMac;
    public CheckBox cbSupportWindows;
    public TextArea taOutput;
    public Button btnClearOutput;

    public static Scene createScene(){
        return new Scene(ViewTool.loadView("MainView.fxml"),600,700);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbSupportAndroid.selectedProperty().addListener((observable, oldValue, newValue) -> {
            containerAndroidPlatform.setManaged(newValue);
            containerAndroidPlatform.setVisible(newValue);
        });
        cbSupportiOS.selectedProperty().addListener((observable, oldValue, newValue) -> {
            containeriOSPlatform.setManaged(newValue);
            containeriOSPlatform.setVisible(newValue);
        });
        cbSupportWindows.selectedProperty().addListener((observable, oldValue, newValue) -> {
            containerWindowsPlatform.setVisible(newValue);
            containerWindowsPlatform.setManaged(newValue);
        });
        cbSupportMac.selectedProperty().addListener((observable, oldValue, newValue) -> {
            containerMacPlatform.setManaged(newValue);
            containerMacPlatform.setVisible(newValue);
        });
    }

    public void btnClearOutputClicked(ActionEvent actionEvent) {
        taOutput.clear();
    }
}
