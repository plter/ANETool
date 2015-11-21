package com.plter.anetool.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by plter on 11/20/15.
 */
public class AneConfigInfo {

    public String swcPath;
    public String airVersion,aneVersion,aneId;

    public boolean supportAndroid=false;
    public String jarOrSoPath;
    public String androidInitializer,androidFinalizer;

    public boolean supportiOS=false;

    public boolean supportWindows=false;

    public boolean supportMac=false;

    public String certPath,certPassword;
    public boolean useTimestamp;

    public String aneOutputPath;

    public String toJSONString(){
        JSONObject jo = new JSONObject();
        try {
            jo.put("swcPath",swcPath);
            jo.put("airVersion",airVersion);
            jo.put("aneVersion",aneVersion);
            jo.put("aneId",aneId);

            jo.put("supportAndroid",supportAndroid);
            if (supportAndroid){
                jo.put("jarOrSoPath",jarOrSoPath);
                jo.put("androidInitializer",androidInitializer);
                jo.put("androidFinalizer",androidFinalizer);
            }

            //TODO support ios,windows,mac

            jo.put("certPath",certPath);
            jo.put("certPassword",certPassword);
            jo.put("useTimestamp",useTimestamp);

            jo.put("aneOutputPath",aneOutputPath);
        } catch (JSONException e) {
            throw new RuntimeException(e.getMessage());
        }

        return jo.toString();
    }

    public String toExtensionXMLContent(){
        StringBuilder content = new StringBuilder();
        content.append(String.format("<extension xmlns=\"http://ns.adobe.com/air/extension/%s\">",airVersion));
        content.append(String.format("<id>%s</id>",aneId));
        content.append(String.format("<versionNumber>%s</versionNumber>",aneVersion));
        content.append("<platforms>");
        if (supportAndroid){
            content.append(String.format("<platform name=\"%s\">","Android-ARM"));
            content.append("<applicationDeployment>");
            content.append(String.format("<nativeLibrary>%s</nativeLibrary>",new File(jarOrSoPath).getName()));
            content.append(String.format("<initializer>%s</initializer>",androidInitializer));
            content.append("</applicationDeployment>");
            content.append("</platform>");
        }
        content.append("</platforms>");
        content.append("</extension>");
        return content.toString();
    }
}
