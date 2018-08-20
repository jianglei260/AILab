package com.sharevar.appstudio.runtime.sdk.script;

import com.sharevar.appstudio.object.function.Function;
import com.sharevar.appstudio.object.function.Void;
import com.sharevar.appstudio.runtime.sdk.FunctionAdapter;

public class IfAdapter extends FunctionAdapter {
    public IfAdapter(Function function) {
        super(function);
    }

    @Override
    public Object invoke() {

        return new Void();
    }
}
