package com.example.appstudio.object;

import com.example.appstudio.object.function.Function;

import java.io.Serializable;

public class Statement implements Serializable {
    private Variable retVaule;
    private Function function;

    public Variable getRetVaule() {
        return retVaule;
    }

    public void setRetVaule(Variable retVaule) {
        this.retVaule = retVaule;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }
}
