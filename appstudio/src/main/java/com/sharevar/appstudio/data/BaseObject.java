package com.sharevar.appstudio.data;

import com.sharevar.appstudio.object.Type;
import com.sharevar.appstudio.runtime.core.RuntimeContext;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseObject {

    private RuntimeContext runtimeContext;

    public RuntimeContext getRuntimeContext() {
        return runtimeContext;
    }

    public void setRuntimeContext(RuntimeContext runtimeContext) {
        this.runtimeContext = runtimeContext;
    }

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
