package com.sharevar.appstudio.object;

import com.sharevar.appstudio.object.function.Function;

import java.io.Serializable;

public class Statement extends Function implements Serializable {
    private Variable retVaule;
    private Function function;
    private boolean debug;
    private boolean watch;


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
}
