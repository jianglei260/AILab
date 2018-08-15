package com.sharevar.appstudio.object.function.builtin;

import com.sharevar.appstudio.object.Type;
import com.sharevar.appstudio.object.function.Function;
import com.sharevar.appstudio.object.function.ReturnType;

public class Loop extends Function {
    CodeBlock codeBlock;

    public Loop() {
        setReturnType(Type.of(Void.class));
    }

    @Override
    public ReturnType invoke() {
        if (codeBlock != null) {
            codeBlock.invoke();
        }
        return new Void();
    }
    @Override
    public String getName() {
        return getClass().getSimpleName();
    }
}
