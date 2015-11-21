package com.plter.anetool.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by plter on 11/21/15.
 */
public class FileTool {


    public static void copyTo(File from,File to){
        try {
            FileInputStream fis = new FileInputStream(from);
            FileOutputStream fos = new FileOutputStream(to);
            byte[] buffer = new byte[1024];
            int size = 0;
            while ((size=fis.read(buffer))!=-1){
                fos.write(buffer,0,size);
            }
            fos.flush();
            fos.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error occur when copy file");
        }
    }

    public static void deleteDirectory(File dir){
        if (dir.isDirectory()){
            File[] files = dir.listFiles();
            if (files!=null){
                for (File f:files) {
                    if (f.isDirectory()){
                        deleteDirectory(f);
                    }else{
                        f.delete();
                    }
                }
            }
            dir.delete();
        }
    }

}
