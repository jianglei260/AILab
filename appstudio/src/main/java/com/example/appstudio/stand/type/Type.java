package com.example.appstudio.stand.type;

public class Type {
    private String type;

    public Type(String type) {
        this.type = type;
    }

    public Type(Class clazz) {
        this.type = clazz.getName();
    }

    public String getType() {
        return type;
    }

    public static Type of(String type) {
        return new Type(type);
    }

    public static Type of(Class clazz) {
        return new Type(clazz);
    }

    public static Type of(Object object) {
        return new Type(object.getClass());
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == null)
            return false;
        if (other == this)
            return true;
        if (other instanceof Type) {
            if (((Type) other).getType().equals(type))
                return true;
            else
                return false;
        }
        return false;
    }
}
