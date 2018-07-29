package com.sharevar.appstudio.object;

public abstract class DynamicObjectRepository extends ObjectRepository {
    private DynamicObject object;

    public DynamicObject getObject() {
        return object;
    }

    public void setObject(DynamicObject object) {
        this.object = object;
    }
}
