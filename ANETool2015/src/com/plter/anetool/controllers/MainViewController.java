package com.plter.anetool.controllers;

import com.plter.anetool.views.CachedTextField;
import com.plter.anetool.views.ViewTool;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {


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
    public Button btnBrowseForAIRSDKPath;
    public VBox rootContainer;
    public TextField tfJdkPath;
    public CachedTextField tfAirSDKPath;
    public CachedTextField tfSwcPath;

    public static Scene createScene() {
        return new Scene(ViewTool.loadView("MainView.fxml"), 800, 700);
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

    public void btnBrowseForJdkPathClickHandler(ActionEvent actionEvent) {
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("浏览JDK/JRE目录");
        File dir = dc.showDialog(rootContainer.getScene().getWindow());
        if (dir != null) {
            tfJdkPath.setText(dir.getAbsolutePath());
        }
    }

    public void btnBrowseForAirSDKPathClickHandler(ActionEvent actionEvent) {
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("浏览Flex/AIR SDK目录");
        File dir = dc.showDialog(rootContainer.getScene().getWindow());
        if (dir != null) {
            tfAirSDKPath.setText(dir.getAbsolutePath());
        }
    }

    public void btnBrowseForSwcPathClickHandler(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.setTitle("浏览SWC文件");
        File dir = fc.showOpenDialog(rootContainer.getScene().getWindow());
        if (dir != null) {
            tfSwcPath.setText(dir.getAbsolutePath());
        }
    }
}
