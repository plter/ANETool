package com.plter.anetool.utils;

import com.plter.anetool.errors.AneToolException;

import java.io.*;

/**
 * Created by plter on 11/21/15.
 */
public class FileTool {


    public static void copyTo(File from, File to) {
        try {
            FileInputStream fis = new FileInputStream(from);
            FileOutputStream fos = new FileOutputStream(to);
            byte[] buffer = new byte[1024];
            int size = 0;
            while ((size = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, size);
            }
            fos.flush();
            fos.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error occur when copy file");
        }
    }

    public static void deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.isDirectory()) {
                        deleteDirectory(f);
                    } else {
                        f.delete();
                    }
                }
            }
            dir.delete();
        }
    }

    public static byte[] getFileBytes(File file) throws IOException {
        FileInputStream fis = null;

        fis = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int size = 0;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            while ((size = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, size);
            }
            fis.close();
            return baos.toByteArray();
        } catch (IOException e) {
            throw e;
        }finally {
            baos.close();
        }
    }

    public static String getFileContent(File file, String charset) throws IOException {
        try {
            return new String(getFileBytes(file), charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

            throw new AneToolException("UnsupportedEncodingException");
        }
    }


    /**
     * Get file content for charset utf-8
     *
     * @param file
     * @return
     */
    public static String getFileContent(File file) throws IOException {
        return getFileContent(file, "UTF-8");
    }


    public static void writeContentToFile(String content,String charset,File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(content.getBytes(charset));
        fos.flush();
        fos.close();
    }


    /**
     * Write string data to file with charset utf-8
     * @param content
     * @param file
     * @throws IOException
     */
    public static void writeContentToFile(String content,File file) throws IOException {
        writeContentToFile(content,"UTF-8",file);
    }

}
