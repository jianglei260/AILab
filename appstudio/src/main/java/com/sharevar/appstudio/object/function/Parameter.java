package com.sharevar.appstudio.object.function;

import com.sharevar.appstudio.data.BaseObject;
import com.sharevar.appstudio.object.Type;

public class Parameter extends BaseObject {
    private Type type;
    private String name;
    private Object defaultValue;
    private Object value;

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }
}
