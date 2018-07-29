package com.sharevar.appstudio.data;

import com.sharevar.appstudio.object.Field;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Entity extends BaseObject{
    private String name;
    private List<Field> fields=new ArrayList<>();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSimpleName(){
        String[] names=name.split("[.]");
        return names[names.length-1];
    }
    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this==obj)
            return true;
        if (!(obj instanceof Entity))
            return false;
        if (this.name.equals(((Entity)obj).name))
            return true;
        return false;
    }
}
