package com.sharevar.appstudio.runtime.core.statement;

import com.sharevar.appstudio.runtime.core.var.Variable;
import com.sharevar.appstudio.runtime.core.function.Function;
import com.sharevar.appstudio.runtime.sdk.FunctionAdapter;

import java.io.Serializable;

public class Statement extends Function implements Serializable {
    private Variable retVaule;
    private Function function;
    private boolean debug;
    private boolean watch;
    private FunctionAdapter functionAdapter;
    private String binderClass;

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

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public boolean isWatch() {
        return watch;
    }

    public void setWatch(boolean watch) {
        this.watch = watch;
    }

    public FunctionAdapter getFunctionAdapter() {
        return functionAdapter;
    }

    public String getBinderClass() {
        return binderClass;
    }

    public void setBinderClass(String binderClass) {
        this.binderClass = binderClass;
    }

    public void setFunctionAdapter(FunctionAdapter functionAdapter) {
        this.functionAdapter = functionAdapter;
    }
}
