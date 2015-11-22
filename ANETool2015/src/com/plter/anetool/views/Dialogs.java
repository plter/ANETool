package com.plter.anetool.views;

import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;

/**
 * Created by plter on 11/20/15.
 */
public class Dialogs {

    public static void showOpenFileDialogAndGetResultToTextField(Window window, String dialogTitle, TextField resultText) {
        FileChooser fc = new FileChooser();
        fc.setTitle(dialogTitle);
        File f = fc.showOpenDialog(window);
        if (f != null) {
            resultText.setText(f.getAbsolutePath());
        }
    }

    public static void showDirectoryDialogAndGetResultToTextField(Window window,String dialogTitle,TextField resultText){
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle(dialogTitle);
        File dir = dc.showDialog(window);
        if (dir != null) {
            resultText.setText(dir.getAbsolutePath());
        }
    }

    public static void showSaveFileDialogAndGetResultToTextField(Window window, String dialogTitle,String initialFileName, TextField resultText) {
        FileChooser fc = new FileChooser();
        fc.setInitialFileName(initialFileName);
        fc.setTitle(dialogTitle);
        File f = fc.showSaveDialog(window);
        if (f != null) {
            resultText.setText(f.getAbsolutePath());
        }
    }
}
