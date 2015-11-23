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

    public static LoadVeiwResult loadView1(String viewName){
        FXMLLoader loader = new FXMLLoader(ViewTool.class.getResource(viewName));
        try {
            Parent p = loader.load();
            return new LoadVeiwResult(loader,p);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("View resource not found for name "+viewName);
        }
    }

    public static class LoadVeiwResult{
        private FXMLLoader loader;
        private Parent view;

        public LoadVeiwResult(FXMLLoader loader, Parent view) {
            this.loader = loader;
            this.view = view;
        }

        public FXMLLoader getLoader() {
            return loader;
        }

        public Parent getView() {
            return view;
        }

        public<T> T getController(){
            return getLoader().getController();
        }
    }
}