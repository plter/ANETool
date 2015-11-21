package com.plter.anetool.res;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by plter on 11/20/15.
 */
public class Images {


    private static Map<String,Image> imageMap = new HashMap<>();

    public static Image getImage(String name){
        Image img = imageMap.get(name);
        if (img==null){
            img = getNewImage(name);
            imageMap.put(name,img);
        }
        return img;
    }

    public static Image getNewImage(String name){
        return new Image(Images.class.getResourceAsStream(name));
    }
}
