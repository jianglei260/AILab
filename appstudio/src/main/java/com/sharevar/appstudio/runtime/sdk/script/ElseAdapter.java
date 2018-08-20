package com.sharevar.appstudio.runtime.sdk.script;

import com.sharevar.appstudio.object.function.Function;
import com.sharevar.appstudio.runtime.sdk.FunctionAdapter;

public class ElseAdapter extends FunctionAdapter {
    public ElseAdapter(Function function) {
        super(function);
    }

    @Override
    public Object invoke() {
        return null;
    }
}
