package com.plter.anetool.views;

import com.plter.anetool.controllers.RecentFilesListCellGraphicViewController;
import com.plter.anetool.models.RecentFilesListCellData;
import javafx.scene.control.ListCell;


/**
 * Created by plter on 11/23/15.
 */
public class RecentFilesListCell extends ListCell<RecentFilesListCellData> implements RecentFilesListCellGraphicViewController.OnRemoveListItemButtonClickListener {

    @Override
    protected void updateItem(RecentFilesListCellData item, boolean empty) {
        super.updateItem(item, empty);

        if (!empty && item != null) {
            ViewTool.LoadVeiwResult loadVeiwResult = ViewTool.loadView1("RecentFilesListCellGraphicView.fxml");
            RecentFilesListCellGraphicViewController controller = loadVeiwResult.getController();
            controller.labelTitle.setText(item.getName());
            controller.txtDecription.setText(item.getAbsolutePath());
            controller.setBindedData(item);
            controller.setOnRemoveListItemButtonClickListener(this);
            setGraphic(loadVeiwResult.getView());
        }else {
            setText(null);
            setGraphic(null);
        }
    }

    @Override
    public void onClick(RecentFilesListCellData data) {
        ((RecentFilesListView)getListView()).removeItem(data);
    }
}
