package com.example.appstudio.object;

import com.example.appstudio.data.BaseObject;
import com.example.appstudio.object.function.Function;

import java.util.List;

public class Type extends BaseObject {
    private String name;
    private Object defaultValue;
    private String note;
    private String parentType;
    private List<Type> attrs;
    private List<Function> functions;


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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getParentType() {
        return parentType;
    }

    public void setParentType(String parentType) {
        this.parentType = parentType;
    }

    public List<Type> getAttrs() {
        return attrs;
    }

    public void setAttrs(List<Type> attrs) {
        this.attrs = attrs;
    }

    public List<Function> getFunctions() {
        return functions;
    }

    public void setFunctions(List<Function> functions) {
        this.functions = functions;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public static Type forName(String name) {
        Type type = new Type();
        type.setName(name);
        return type;
    }

    public static Type forObject(Object obj) {
        Type type = new Type();
        type.setName(obj.getClass().getName());
        return type;
    }

    public Class classType() {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Object.class;
    }
}
