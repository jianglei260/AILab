package com.sharevar.appstudio.ui.common;

import java.util.HashMap;
import java.util.Map;

public abstract class ClassLinkRule {
    private Map<Class,Class<? extends RecyclerViewBinder>> rule=new HashMap<>();


    public  void add(Class keyType,Class<? extends RecyclerViewBinder> binder){
        rule.put(keyType,binder);
    }

    public Class<? extends RecyclerViewBinder> binderClass(Object object){
        return rule.get(keyType(object));
    }

    public abstract Class keyType(Object o);
}
