package com.example.appstudio.utils;

public class StringUtil {
    public static String getSimpleName(Class clazz){
        return clazz.getSimpleName();
    }
    public static String getSimpleName(String clazzName){
        String simpleName = clazzName;
        final int dot = simpleName.lastIndexOf(".");
        if (dot > 0) {
            return simpleName.substring(simpleName.lastIndexOf(".")+1); // strip the package name
        }

        return simpleName;
    }
}
