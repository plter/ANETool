package com.plter.anetool.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * Created by plter on 11/17/15.
 */
public class ViewTool {

    public static Parent loadView(String viewName){
        try {
            return FXMLLoader.load(ViewTool.class.getResource(viewName));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("View resource not found for name "+viewName);
        }
    }
}