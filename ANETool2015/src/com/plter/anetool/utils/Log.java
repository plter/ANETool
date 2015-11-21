package com.plter.anetool.utils;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

/**
 * Created by plter on 11/20/15.
 */
public class Log {

    private static TextArea outputTextArea = null;

    public static void setOutputTextArea(TextArea outputTextArea) {
        Log.outputTextArea = outputTextArea;
    }

    public static TextArea getOutputTextArea() {
        return outputTextArea;
    }

    public static void info(String msg){
        if (outputTextArea!=null){
            outputTextArea.appendText(String.format("[Info]%s\n",msg));

            int len = outputTextArea.getLength();
            outputTextArea.selectRange(len,len);
        }
    }

    public static void error(String msg){
        if (outputTextArea!=null){
            outputTextArea.appendText(String.format("[Error]%s\n",msg));

            int len = outputTextArea.getLength();
            outputTextArea.selectRange(len,len);
        }
    }

    public static void errorFromAnotherThread(String msg){
        Platform.runLater(() -> error(msg));
    }

    public static void infoFromAnotherThread(String msg){
        Platform.runLater(() -> info(msg));
    }
}
