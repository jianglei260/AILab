package com.sharevar.appstudio.runtime.sdk;

import com.sharevar.appstudio.object.function.Function;

public abstract class FunctionAdapter {
    protected Function function;

    public FunctionAdapter(Function function) {
        this.function = function;
    }

    public  Function getFunction(){
        return function;
    }

    public abstract Object invoke();
}
