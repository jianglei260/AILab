package com.sharevar.appstudio.object;

import com.sharevar.appstudio.data.BaseObject;
import com.sharevar.appstudio.runtime.core.function.Function;

import java.util.Collection;
import java.util.Date;

public class Type extends BaseObject {
    private String name;
    private Object defaultValue;
    private String note;
    private String parentType;
    private Type parameterizedType;
    public String objectId="";
    public Date createdAt=new Date();
    public Date updateAt=new Date();

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getTypeName() {
        return getClass().getSimpleName();
    }

    public Type() {
    }

    public Type(String name) {
        this.name = name;
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

    public Type getParameterizedType() {
        return parameterizedType;
    }
    public String getType() {
        return name;
    }
    public void setParameterizedType(Type parameterizedType) {
        this.parameterizedType = parameterizedType;
    }

    public static Type of(String type) {
        return new Type(type);
    }

    public static Type of(Class clazz) {
        return new Type(clazz.getName());
    }

    public static Type of(Object object) {
        return new Type(object.getClass().getName());
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == null)
            return false;
        if (other == this)
            return true;
        if (other instanceof Type) {
            if (((Type) other).getName().equals(name))
                return true;
            else
                return false;
        }
        return false;
    }

    public boolean isCollection(){
        try {
            return Collection.class.isAssignableFrom(Class.forName(name));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Type forName(String name) {
        Type type = new Type(name);
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
