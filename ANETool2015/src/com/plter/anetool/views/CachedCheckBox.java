package com.plter.anetool.views;

import com.plter.anetool.data.Config;
import javafx.scene.control.CheckBox;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Created by plter on 11/19/15.
 */
public class CachedCheckBox extends CheckBox{

    private Preferences pref = Preferences.userRoot().node(Config.LOCAL_CACHE_NODE_NAME).node("CheckBox");

    private String cacheKey = null;

    public CachedCheckBox() {

        selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (cacheKey!=null){
                pref.putBoolean(cacheKey,newValue);
                try {
                    pref.flush();
                } catch (BackingStoreException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;

        if (cacheKey!=null){
            setSelected(pref.getBoolean(cacheKey,false));
        }
    }

    public String getCacheKey() {
        return cacheKey;
    }
}
