package com.example.appstudio.data;

import com.example.appstudio.object.Type;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseObject {
    private String objectId;
    private String createdAt;
    private String updateAt;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public String getTypeName() {
        return getClass().getSimpleName();
    }

    public Map<String, Object> attrs() {
        Field[] fields = getClass().getFields();
        HashMap<String, Object> attrs = new HashMap<>();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                attrs.put(field.getName(), field.get(this));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return attrs;
    }
}
