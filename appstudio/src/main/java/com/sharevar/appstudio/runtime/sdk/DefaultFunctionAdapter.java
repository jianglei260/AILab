package com.sharevar.appstudio.runtime.sdk;

import com.sharevar.appstudio.runtime.core.function.Function;

public class DefaultFunctionAdapter extends FunctionAdapter {
    public DefaultFunctionAdapter(Function function) {
        super(function);
    }

    @Override
    public Object invoke() {
        return function.invoke();
    }
}
