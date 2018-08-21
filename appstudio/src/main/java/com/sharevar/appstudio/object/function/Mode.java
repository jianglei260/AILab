package com.sharevar.appstudio.object.function;

import java.util.ArrayList;
import java.util.List;

public class Mode {
    private String name;
    private List<Parameter> parameters=new ArrayList<>();
    private int paramsNum;


    public int getParamsNum() {
        return paramsNum;
    }

    public void setParamsNum(int paramsNum) {
        this.paramsNum = paramsNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }
}
