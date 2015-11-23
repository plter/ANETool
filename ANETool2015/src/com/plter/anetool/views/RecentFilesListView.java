package com.plter.anetool.views;

import com.plter.anetool.data.Config;
import com.plter.anetool.models.RecentFilesListCellData;
import com.plter.anetool.utils.ArrayTool;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.io.File;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;

/**
 * Created by plter on 11/23/15.
 */
public class RecentFilesListView extends ListView<RecentFilesListCellData> {


    private Preferences prefs = Preferences.userRoot().node(Config.LOCAL_CACHE_NODE_NAME).node("recentFileList");


    public RecentFilesListView() {
        setCellFactory(param -> new RecentFilesListCell());
        readCachedData();
    }

    private void readCachedData() {
        String cachedFiles = prefs.get("recentFiles", null);

        ObservableList<RecentFilesListCellData> items = getItems();
        if (cachedFiles != null && !cachedFiles.equals("")) {
            String[] filePaths = cachedFiles.split("\n");
            for (int i = 0; i < filePaths.length; i++) {
                items.add(new RecentFilesListCellData(new File(filePaths[i])));
            }
        }
    }


    public void addItem(RecentFilesListCellData data) {

        ObservableList<RecentFilesListCellData> items = getItems();

        int index = items.indexOf(data);

        if (index != -1) {
            items.remove(index);
        }

        items.add(0, data);
        saveRecentFiles();
    }

    private void saveRecentFiles() {
        ObservableList<RecentFilesListCellData> items = getItems();
        if (items.size()>0) {
            prefs.put("recentFiles", ArrayTool.join(items.stream().map(RecentFilesListCellData::getAbsolutePath).collect(Collectors.toList()), "\n"));
            try {
                prefs.flush();
            } catch (BackingStoreException e) {
                e.printStackTrace();
            }
        }else {
            try {
                prefs.clear();
            } catch (BackingStoreException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeItem(RecentFilesListCellData data) {
        getItems().remove(data);
        saveRecentFiles();
    }
}
