package com.plter.anetool.controllers;

import com.plter.anetool.models.RecentFilesListCellData;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Created by plter on 11/23/15.
 */
public class RecentFilesListCellGraphicViewController {
    public VBox rootContainer;


    public Label labelTitle;
    public Text txtDecription;
    public Button btnClose;
    private RecentFilesListCellData bindedData;

    public void mouseEnterHandler(Event event) {
        btnClose.setManaged(true);
        btnClose.setVisible(true);
    }

    public void mouseExitedHandler(Event event) {
        btnClose.setManaged(false);
        btnClose.setVisible(false);
    }

    public void btnCloseClickedHandler(ActionEvent actionEvent) {
        if (getOnRemoveListItemButtonClickListener()!=null){
            getOnRemoveListItemButtonClickListener().onClick(getBindedData());
        }
    }

    public void setBindedData(RecentFilesListCellData bindedData) {
        this.bindedData = bindedData;
    }

    public RecentFilesListCellData getBindedData() {
        return bindedData;
    }

    private OnRemoveListItemButtonClickListener onRemoveListItemButtonClickListener=null;

    public void setOnRemoveListItemButtonClickListener(OnRemoveListItemButtonClickListener onRemoveListItemButtonClickListener) {
        this.onRemoveListItemButtonClickListener = onRemoveListItemButtonClickListener;
    }

    public OnRemoveListItemButtonClickListener getOnRemoveListItemButtonClickListener() {
        return onRemoveListItemButtonClickListener;
    }

    public interface OnRemoveListItemButtonClickListener{
        void onClick(RecentFilesListCellData data);
    }
}
