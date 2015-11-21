package com.plter.anetool.controllers;

import com.plter.anetool.core.PkgAneTool;
import com.plter.anetool.data.Config;
import com.plter.anetool.models.AneConfigInfo;
import com.plter.anetool.utils.Log;
import com.plter.anetool.views.*;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {


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
    public CachedTextField tfAirSDKPath;
    public CachedTextField tfSwcPath;
    public Button btnBrowseForAndroidJarOrSoPath;
    public CachedTextField tfJarOrSoPath;
    public CachedTextField tfCertPath;
    public Button btnBrowseForCertPath;
    public CachedPasswordField tfCertPassword;
    public CachedTextField tfAneOutputPath;
    public CachedTextField tfAndroidInitializer;
    public CachedTextField tfAndroidFinalizer;
    public CachedCheckBox cbUseTimestamp;
    public CachedTextField tfAirVersion;
    public CachedTextField tfAneId;
    public CachedTextField tfAneVersion;
    private Window window;
    private File configFile=null;//The ane tool config file

    public static Scene createScene() {
        return new Scene(ViewTool.loadView("MainView.fxml"), 1000, 700);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Log.setOutputTextArea(taOutput);

        syncContainerAndroidPlatformState();
        cbSupportAndroid.selectedProperty().addListener((observable, oldValue, newValue) -> {
            syncContainerAndroidPlatformState();
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

    private void syncContainerAndroidPlatformState() {
        containerAndroidPlatform.setManaged(cbSupportAndroid.isSelected());
        containerAndroidPlatform.setVisible(cbSupportAndroid.isSelected());
    }

    public Window getWindow() {
        if (window==null){
            window = rootContainer.getScene().getWindow();
        }
        return window;
    }

    public void btnClearOutputClicked(ActionEvent actionEvent) {
        taOutput.clear();
    }

    public void btnBrowseForAirSDKPathClickHandler(ActionEvent actionEvent) {
        Dialogs.showDirectoryDialogAndGetResultToTextField(getWindow(),"浏览Flex/AIR SDK目录",tfAirSDKPath);
    }

    public void btnBrowseForSwcPathClickHandler(ActionEvent actionEvent) {
        Dialogs.showOpenFileDialogAndGetResultToTextField(getWindow(),"浏览swc文件",tfSwcPath);
    }

    public void btnBrowseForAndroidJarOrSoPathClickHandler(ActionEvent actionEvent) {
        Dialogs.showOpenFileDialogAndGetResultToTextField(getWindow(),"浏览jar/so文件",tfJarOrSoPath);
    }

    public void btnBrowseForCertPathClickHandler(ActionEvent actionEvent) {
        Dialogs.showOpenFileDialogAndGetResultToTextField(getWindow(),"浏览证书文件",tfCertPath);
    }

    public void btnAboutClickHandler(ActionEvent actionEvent) {
        AboutViewController.openNewWindow(getWindow());
    }

    public void btnAneSavedPathClickHandler(ActionEvent actionEvent) {
        Dialogs.showSaveFileDialogAndGetResultToTextField(getWindow(),"选择ANE文件的保存路径", tfAneOutputPath);
    }

    public void btnDonateClickHandler(ActionEvent actionEvent) {
        DonateViewController.openNewWindow(getWindow());
    }

    public void btnStartGenAneClickedHandler(ActionEvent actionEvent) {
        PkgAneTool.startPkgAne(tfAirSDKPath.getText(),makeAneConfigInfo());
    }

    private AneConfigInfo makeAneConfigInfo(){
        AneConfigInfo info = new AneConfigInfo();
        info.swcPath = tfSwcPath.getText();
        info.airVersion = tfAirVersion.getText();
        info.aneId = tfAneId.getText();
        info.aneVersion = tfAneVersion.getText();

        info.supportAndroid = cbSupportAndroid.isSelected();
        if (info.supportAndroid) {
            info.jarOrSoPath = tfJarOrSoPath.getText();
            info.androidInitializer = tfAndroidInitializer.getText();
            info.androidFinalizer = tfAndroidFinalizer.getText();
        }
        //TODO support ios,windows,mac

        info.certPath = tfCertPath.getText();
        info.certPassword = tfCertPassword.getText();
        info.useTimestamp = cbUseTimestamp.isSelected();

        info.aneOutputPath = tfAneOutputPath.getText();
        return info;
    }

    public void btnSaveConfigFileClickedHandler(ActionEvent actionEvent) {
        if (configFile==null){
            FileChooser fc = new FileChooser();
            fc.setTitle("请选择配置文件保存地址");
            fc.setInitialFileName("BuildAne.atconfig");
            configFile = fc.showSaveDialog(getWindow());
        }

        if (configFile!=null){
            try {
                if (!configFile.exists()){
                    configFile.createNewFile();
                }

                FileOutputStream fos = new FileOutputStream(configFile);
                fos.write(makeAneConfigInfo().toJSONString().getBytes(Config.DEFAULT_CHARSET));
                fos.flush();
                fos.close();

                Log.info("保存文件成功");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
