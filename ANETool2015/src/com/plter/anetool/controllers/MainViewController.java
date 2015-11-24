package com.plter.anetool.controllers;

import com.plter.anetool.core.PkgAneOpt;
import com.plter.anetool.data.Config;
import com.plter.anetool.models.AneConfigInfo;
import com.plter.anetool.models.RecentFilesListCellData;
import com.plter.anetool.utils.FileTool;
import com.plter.anetool.utils.Log;
import com.plter.anetool.views.CachedTextField;
import com.plter.anetool.views.Dialogs;
import com.plter.anetool.views.RecentFilesListView;
import com.plter.anetool.views.ViewTool;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.json.JSONException;

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
    public TextField tfSwcPath;
    public Button btnBrowseForAndroidJarPath;
    public TextField tfJarPath;
    public TextField tfCertPath;
    public Button btnBrowseForCertPath;
    public PasswordField tfCertPassword;
    public TextField tfAneOutputPath;
    public TextField tfAndroidInitializer;
    public CheckBox cbUseTimestamp;
    public TextField tfAirVersion;
    public TextField tfAneId;
    public TextField tfAneVersion;
    public RecentFilesListView lvRecentConfigFiles;
    private Window window;
    private File currentConfigFile = null;//The ane tool config file

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

        initRecentConfigFilesListView();
    }

    private void initRecentConfigFilesListView() {
        lvRecentConfigFiles.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null) {
                syncUIStatusWithConfigFile(newValue.getFile());
            }
        });
        lvRecentConfigFiles.getSelectionModel().select(0);
    }

    private void syncContainerAndroidPlatformState() {
        containerAndroidPlatform.setManaged(cbSupportAndroid.isSelected());
        containerAndroidPlatform.setVisible(cbSupportAndroid.isSelected());
    }

    public Window getWindow() {
        if (window == null) {
            window = rootContainer.getScene().getWindow();
        }
        return window;
    }

    public void btnClearOutputClicked(ActionEvent actionEvent) {
        taOutput.clear();
    }

    public void btnBrowseForAirSDKPathClickHandler(ActionEvent actionEvent) {
        Dialogs.showDirectoryDialogAndGetResultToTextField(getWindow(), "浏览Flex/AIR SDK目录", tfAirSDKPath);
    }

    public void btnBrowseForSwcPathClickHandler(ActionEvent actionEvent) {
        Dialogs.showOpenFileDialogAndGetResultToTextField(getWindow(), "浏览swc文件", tfSwcPath);
    }

    public void btnBrowseForAndroidJarPathClickHandler(ActionEvent actionEvent) {
        Dialogs.showOpenFileDialogAndGetResultToTextField(getWindow(), "浏览jar/so文件", tfJarPath);
    }

    public void btnBrowseForCertPathClickHandler(ActionEvent actionEvent) {
        Dialogs.showOpenFileDialogAndGetResultToTextField(getWindow(), "浏览证书文件", tfCertPath);
    }

    public void btnAboutClickHandler(ActionEvent actionEvent) {
        AboutViewController.openNewWindow(getWindow());
    }

    public void btnAneSavedPathClickHandler(ActionEvent actionEvent) {
        Dialogs.showSaveFileDialogAndGetResultToTextField(getWindow(), "选择ANE文件的保存路径", "FlashLib.ane", tfAneOutputPath);
    }

    public void btnDonateClickHandler(ActionEvent actionEvent) {
        DonateViewController.openNewWindow(getWindow());
    }

    public void btnStartGenAneClickedHandler(ActionEvent actionEvent) {
        PkgAneOpt.startPkgAne(tfAirSDKPath.getText(), makeAneConfigInfo());
    }

    private AneConfigInfo makeAneConfigInfo() {
        AneConfigInfo info = new AneConfigInfo();
        info.swcPath = tfSwcPath.getText();
        info.airVersion = tfAirVersion.getText();
        info.aneId = tfAneId.getText();
        info.aneVersion = tfAneVersion.getText();

        info.supportAndroid = cbSupportAndroid.isSelected();
        if (info.supportAndroid) {
            info.jarPath = tfJarPath.getText();
            info.androidInitializer = tfAndroidInitializer.getText();
        }
        //TODO support ios,windows,mac

        info.certPath = tfCertPath.getText();
        info.certPassword = tfCertPassword.getText();
        info.useTimestamp = cbUseTimestamp.isSelected();

        info.aneOutputPath = tfAneOutputPath.getText();
        return info;
    }

    public void btnSaveConfigFileClickedHandler(ActionEvent actionEvent) {

        boolean firstSave = false;

        if (currentConfigFile == null) {
            FileChooser fc = new FileChooser();
            fc.setTitle("请选择配置文件保存地址");
            fc.setInitialFileName("BuildAne.atconfig");
            currentConfigFile = fc.showSaveDialog(getWindow());

            firstSave = true;
        }

        if (currentConfigFile != null) {
            try {
                FileTool.writeContentToFile(makeAneConfigInfo().toJSONString(),currentConfigFile);
                Log.info("保存文件成功");

                if (firstSave){
                    lvRecentConfigFiles.addAndSelectItem(new RecentFilesListCellData(currentConfigFile));
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.error("保存文件失败");
            }
        }
    }

    public void syncUiStatusWithConfigInfo(AneConfigInfo info) {
        tfSwcPath.setText(info.swcPath);
        tfAirVersion.setText(info.airVersion);
        tfAneVersion.setText(info.aneVersion);
        tfAneId.setText(info.aneId);
        cbSupportAndroid.setSelected(info.supportAndroid);
        if (cbSupportAndroid.isSelected()) {
            tfJarPath.setText(info.jarPath);
            tfAndroidInitializer.setText(info.androidInitializer);
        }
        tfCertPath.setText(info.certPath);
        tfCertPassword.setText(info.certPassword);
        cbUseTimestamp.setSelected(info.useTimestamp);
        tfAneOutputPath.setText(info.aneOutputPath);
    }

    private void syncUIStatusWithConfigFile(File file){
        currentConfigFile = file;

        try {
            String jsonStr = FileTool.getFileContent(file);
            AneConfigInfo info = AneConfigInfo.fromJsonString(jsonStr);
            syncUiStatusWithConfigInfo(info);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.error("配置文件格式错误");
        } catch (IOException e) {
            e.printStackTrace();
            Log.error("打开配置文件出错");
        }
    }

    public void btnOpenConfigFileClickedHandler(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.setTitle("打开配置文件");
        File file = fc.showOpenDialog(getWindow());
        if (file != null) {
            lvRecentConfigFiles.addAndSelectItem(new RecentFilesListCellData(file));
        }
    }

    public void btnSaveNewConfigFileClickedHandler(ActionEvent actionEvent) {
        if (currentConfigFile==null){
            btnSaveConfigFileClickedHandler(null);
        }else {
            FileChooser fc = new FileChooser();
            fc.setTitle("请选择配置文件保存地址");
            fc.setInitialFileName("CopyOf"+currentConfigFile.getName());
            File result = fc.showSaveDialog(getWindow());

            if (result!=null){
                currentConfigFile = result;

                try {
                    FileTool.writeContentToFile(makeAneConfigInfo().toJSONString(),currentConfigFile);

                    lvRecentConfigFiles.addAndSelectItem(new RecentFilesListCellData(currentConfigFile));

                    Log.info("另存文件成功");
                } catch (IOException e) {
                    e.printStackTrace();

                    Log.error("另存文件失败");
                }
            }
        }
    }
}
