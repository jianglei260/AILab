package com.sharevar.appstudio.runtime.core.var;

import com.sharevar.appstudio.data.BaseObject;
import com.sharevar.appstudio.object.Type;
import com.sharevar.appstudio.runtime.core.RuntimeContext;

public class Variable extends BaseObject {
    private Type type;
    private String name;
    private Object value;


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

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
