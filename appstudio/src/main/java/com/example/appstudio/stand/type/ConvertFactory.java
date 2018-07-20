package com.example.appstudio.stand.type;

public class ConvertFactory {
    public static Integer toInteger(int value) {
        return Integer.valueOf(value);
    }

    public static Integer toInteger(String value) {
        return Integer.valueOf(value);
    }

    public static String toString(int value) {
        return String.valueOf(value);
    }

    public static String toString(Integer value) {
        return String.valueOf(value);
    }

    public static String toString(float value) {
        return String.valueOf(value);
    }

    public static String toString(Float value) {
        return String.valueOf(value);
    }

    public static String toString(long value) {
        return String.valueOf(value);
    }

    public static String toString(Long value) {
        return String.valueOf(value);
    }

    public static long toLong(int value) {
        return Long.valueOf(value);
    }

    public static long toLong(String value) {
        return Long.valueOf(value);
    }

    public static Long toLong(long value) {
        return Long.valueOf(value);
    }

    public static Type toType(Class clazz){
        return Type.of(clazz);
    }
    public static Class toClass(String clazz){
        try {
            return Class.forName(clazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
