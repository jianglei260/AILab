package com.sharevar.appstudio.common.object;

import com.sharevar.appstudio.common.string.StringUtil;
import com.sharevar.appstudio.data.Attr;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ObjectTool {
    public static List<Attr> attrs(Object object) {
        Class clazz = object.getClass();
        try {
            if (clazz.getMethod("attrs") != null) {
                return (List<Attr>) clazz.getMethod("attrs").invoke(object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return findAttrs(object);
    }

    public static List<Attr> findAttrs(Object object) {
        Class clazz = object.getClass();
        Method[] methods = clazz.getMethods();
        List<Attr> attrs = new ArrayList<>();
        try {
            for (Method method : methods) {
                String name = method.getName();
                String attrName = "";
                String attrType = "";
                Object attrValue = null;
                if (name.startsWith("is")) {
                    attrName = StringUtil.lowerFirstChar(name.replace("is", ""));
                    attrValue = method.invoke(object);
                    attrType = attrValue.getClass().getName();
                } else if (name.startsWith("get")) {
                    attrName = StringUtil.lowerFirstChar(name.replace("get", ""));
                    attrValue = method.invoke(object);
                    attrType = attrValue.getClass().getName();
                } else if (name.startsWith("set")) {
                    attrName = StringUtil.lowerFirstChar(name.replace("set", ""));
                }
                if (!StringUtil.isNullOrEmpty(attrName)) {
                    Attr attr = new Attr();
                    attr.setName(attrName);
                    attr.setValue(attrValue);
                    attr.setType(attrType);
                    attrs.add(attr);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return attrs;
    }

    public static Method getMethod(Class clazz, String name) {
        char[] chars = name.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        String methodName = "get" + String.valueOf(chars);
        try {
            Method method = clazz.getMethod(methodName);
            return method;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Method setMethod(Class clazz, String name) {
        char[] chars = name.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        String methodName = "set" + String.valueOf(chars);
        Method getMethod = getMethod(clazz, name);
        if (getMethod == null)
            return null;
        try {
            Method method = clazz.getMethod(methodName, getMethod.getReturnType());
            return method;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
