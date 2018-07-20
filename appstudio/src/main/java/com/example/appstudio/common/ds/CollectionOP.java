package com.example.appstudio.common.ds;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

public class CollectionOP<T> {
    public static <T> T findByAttr(Collection<T> collection, String attr, Object value) {
        for (T t : collection) {
            try {
                Field field = t.getClass().getField(attr);
                field.setAccessible(true);
                if (value.equals(field.get(t)))
                    return t;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
