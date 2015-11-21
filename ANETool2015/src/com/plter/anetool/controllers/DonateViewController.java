package com.plter.anetool.controllers;

import com.plter.anetool.res.Images;
import com.plter.anetool.res.Strings;
import com.plter.anetool.views.ViewTool;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by plter on 11/20/15.
 */
public class DonateViewController implements Initializable{

    public ImageView imgAlipayDonate;
    public ImageView imgWechatDonate;
    public VBox rootContainer;

    public static Scene createScene(){
        return new Scene(ViewTool.loadView("DonateView.fxml"),700,600);
    }

    public static Stage openNewWindow(Window parent){
        Stage s = new Stage();
        s.initOwner(parent);
        s.initModality(Modality.WINDOW_MODAL);
        s.setScene(createScene());
        s.setTitle("捐助 "+ Strings.TITLE);
        s.show();
        return s;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imgAlipayDonate.setImage(Images.getImage("alipay_donate.png"));
        imgWechatDonate.setImage(Images.getImage("wechat_donate.jpg"));
    }

    private Stage stage;

    public Stage getStage() {
        if (stage==null){
            stage = (Stage) rootContainer.getScene().getWindow();
        }
        return stage;
    }

    public void btnCloseClickHandler(ActionEvent actionEvent) {
        getStage().close();
    }
}
