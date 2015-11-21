package com.plter.anetool.views;

import com.plter.anetool.data.Config;
import javafx.scene.control.PasswordField;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Created by plter on 11/20/15.
 */
public class CachedPasswordField extends PasswordField {
    private Preferences pref = Preferences.userRoot().node(Config.LOCAL_CACHE_NODE_NAME).node("PasswordField");

    public CachedPasswordField() {

        textProperty().addListener((observable, oldValue, newValue) -> {
            if (cacheKey != null) {
                pref.put(cacheKey, this.getText());
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
