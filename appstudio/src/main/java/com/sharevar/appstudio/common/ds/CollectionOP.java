package com.sharevar.appstudio.common.ds;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CollectionOP<T> {
    public static <T> T findByAttr(Collection<T> collection, String attr, Object value) {
        char[] chars = attr.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        String methodName = "get" + String.valueOf(chars);
        for (T t : collection) {
            try {
                Method method = t.getClass().getMethod(methodName);
                if (value.equals(method.invoke(t)))
                    return t;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static <T> T findByAttr(T[] collection, String attr, Object value) {
        return findByAttr(Arrays.asList(collection), attr, value);
    }
}
