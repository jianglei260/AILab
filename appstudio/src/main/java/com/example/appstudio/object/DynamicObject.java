package com.example.appstudio.object;

import com.example.appstudio.data.BaseObject;

import java.util.HashMap;
import java.util.Map;

public class DynamicObject extends BaseObject {
    private Type type;
    private Map<String, Object> attrs;

    public DynamicObject(Type type) {
        this.type = type;
        attrs = new HashMap<>();
    }

    public void set(String name, Object value) {
        attrs.put(name, value);
    }

    public Object get(String name) {
        return attrs.get(name);
    }

    @Override
    public String getTypeName() {
        return type.getTypeName();
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Map<String, Object> getAttrs() {
        return attrs;
    }

    public void setAttrs(Map<String, Object> attrs) {
        this.attrs = attrs;
    }
}
