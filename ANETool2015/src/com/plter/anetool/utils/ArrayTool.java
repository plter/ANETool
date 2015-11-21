package com.plter.anetool.utils;

import java.util.List;

/**
 * Created by plter on 11/21/15.
 */
public class ArrayTool {

    public static String join(List<String> list,String sep){
        StringBuilder content = new StringBuilder();

        for (String str:list) {
            content.append(String.format("%s ",str));
        }
        content.deleteCharAt(content.length()-1);

        return content.toString();
    }
}
