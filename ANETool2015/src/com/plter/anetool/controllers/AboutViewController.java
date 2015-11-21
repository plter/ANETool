package com.plter.anetool.controllers;

import com.plter.anetool.res.Strings;
import com.plter.anetool.views.ViewTool;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by plter on 11/20/15.
 */
public class AboutViewController implements Initializable {


    public VBox rootContainer;
    public Text txtVersion;

    public static Scene createScene() {
        return new Scene(ViewTool.loadView("AboutView.fxml"), 380, 200);
    }

    public static void openNewWindow(Window parent) {
        Stage s = new Stage();
        s.initOwner(parent);
        s.initModality(Modality.WINDOW_MODAL);
        s.setTitle("关于 " + Strings.TITLE);
        s.setScene(createScene());
        s.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtVersion.setText(Strings.VERSION);
    }

    private Stage window;

    public Stage getWindow() {
        if (window == null) {
            window = (Stage) rootContainer.getScene().getWindow();
        }
        return window;
    }

    public void btnCloseClickHandler(ActionEvent actionEvent) {
        getWindow().close();
    }


    public void hyperLinkClickedHandler(ActionEvent actionEvent) {
        try {
            Desktop.getDesktop().browse(new URI(((Hyperlink)actionEvent.getSource()).getText()));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
