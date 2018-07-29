package com.sharevar.appstudio.data;

import com.sharevar.appstudio.object.Type;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseObject {

    public String getTypeName() {
        return getClass().getSimpleName();
    }

    public Map<String, Object> attrs() {
        Field[] fields = getClass().getFields();
        HashMap<String, Object> attrs = new HashMap<>();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                attrs.put(field.getName(), field.get(this));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return attrs;
    }
}
