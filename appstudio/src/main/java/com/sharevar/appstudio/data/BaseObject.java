package com.sharevar.appstudio.data;

import com.sharevar.appstudio.object.Type;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseObject {

    public String getTypeName() {
        return getClass().getSimpleName();
    }

    public List<Attr> attrs() {
        Field[] fields = getClass().getFields();
        List<Attr> attrs = new ArrayList<>();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Attr attr=new Attr();
                attr.setName(field.getName());
                attr.setValue(field.get(this));
                attr.setType(field.getType().getName());
                attrs.add(attr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return attrs;
    }
}
