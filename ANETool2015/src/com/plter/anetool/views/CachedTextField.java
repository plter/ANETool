package com.plter.anetool.views;

import com.plter.anetool.data.Config;
import javafx.scene.control.TextField;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Created by plter on 11/19/15.
 */
public class CachedTextField extends TextField {

    private Preferences pref = Preferences.userRoot().node(Config.LOCAL_CACHE_NODE_NAME);

    public CachedTextField() {

        textProperty().addListener((observable, oldValue, newValue) -> {
            if (cacheKey != null) {
                pref.put(cacheKey, getText());
                try {
                    pref.flush();
                } catch (BackingStoreException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String cacheKey = null;

    public String getCacheKey() {
        return cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;

        if (cacheKey != null) {
            setText(pref.get(cacheKey, ""));
        }
    }
}
