package com.example.appstudio.object.function;

import com.example.appstudio.data.BaseObject;

import java.util.List;

public abstract class Function extends BaseObject {
    protected List<Parameter> parameters;
    protected ReturnType returnType;
    protected String name;


    public abstract Object invoke();

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public ReturnType getReturnType() {
        return returnType;
    }

    public void setReturnType(ReturnType returnType) {
        this.returnType = returnType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
