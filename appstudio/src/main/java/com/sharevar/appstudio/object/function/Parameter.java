package com.sharevar.appstudio.object.function;

import com.sharevar.appstudio.data.BaseObject;
import com.sharevar.appstudio.object.Type;

import java.util.ArrayList;
import java.util.List;

public class Parameter extends BaseObject {
    private Type type;
    private String name;
    private Object defaultValue;
    private Object value;
    private boolean require;
    private List<Option> options=new ArrayList<>();

    public boolean isRequire() {
        return require;
    }

    public void setRequire(boolean require) {
        this.require = require;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

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
