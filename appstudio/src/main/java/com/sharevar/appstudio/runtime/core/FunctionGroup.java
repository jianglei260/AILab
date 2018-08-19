package com.sharevar.appstudio.runtime.core;

import com.sharevar.appstudio.object.function.Function;

import java.util.List;

public class FunctionGroup {
    private String name;
    private List<Function> functions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Function> getFunctions() {
        return functions;
    }

    public void setFunctions(List<Function> functions) {
        this.functions = functions;
    }
}
