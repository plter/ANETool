package com.plter.anetool.utils;

import java.util.List;

/**
 * Created by plter on 11/21/15.
 */
public class ArrayTool {

    public static String join(List<?> list,String sep){
        if (list.size()>0) {
            StringBuilder content = new StringBuilder();

            for (Object str : list) {
                content.append(String.format("%s%s", str.toString(), sep));
            }
            content.deleteCharAt(content.length() - 1);

            return content.toString();
        }else {
            return null;
        }
    }
}
