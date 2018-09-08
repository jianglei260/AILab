package com.sharevar.appstudio.runtime.core.function;

import com.sharevar.appstudio.common.string.StringUtil;
import com.sharevar.appstudio.data.BaseObject;
import com.sharevar.appstudio.object.Type;
import com.sharevar.appstudio.runtime.core.RuntimeContext;
import com.sharevar.appstudio.runtime.sdk.FunctionAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Function extends BaseObject {
    protected List<Mode> modes = new ArrayList<>();
    protected Type returnType;
    protected String name;
    private String path;

    public String objectId = "";
    public Date createdAt;
    public Date updateAt;

    protected String tag;

    protected String adapter;
    protected String parent;

    protected String desc;
    private RuntimeContext runtimeContext;

    private int modeIndex;


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

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


    public Object invoke() {
        if (!StringUtil.isNullOrEmpty(adapter)) {
            try {
                Class<FunctionAdapter> adapterClazz = (Class<FunctionAdapter>) Class.forName(adapter);
                FunctionAdapter functionAdapter = adapterClazz.getConstructor(Function.class).newInstance(this);
                return functionAdapter.invoke();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getModeIndex() {
        return modeIndex;
    }

    public void setModeIndex(int modeIndex) {
        this.modeIndex = modeIndex;
    }

    public RuntimeContext getRuntimeContext() {
        return runtimeContext;
    }

    public void setRuntimeContext(RuntimeContext runtimeContext) {
        this.runtimeContext = runtimeContext;
    }

    public List<Mode> getModes() {
        return modes;
    }

    public void setModes(List<Mode> modes) {
        this.modes = modes;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getAdapter() {
        return adapter;
    }


    public void setAdapter(String adapter) {
        this.adapter = adapter;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<Parameter> getParameters() {
        return modes.get(0).getParameters();
    }

    public void setParameters(List<Parameter> parameters) {
        this.modes.get(0).setParameters(parameters);
    }

    public Type getReturnType() {
        return returnType;
    }


    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
