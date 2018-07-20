package com.example.appstudio.object.function.builtin;

import com.example.appstudio.object.function.Function;
import com.example.appstudio.object.function.ReturnType;

public class If extends Function {
    CodeBlock codeBlock;

    @Override
    public ReturnType invoke() {
        if (codeBlock != null) {
            codeBlock.invoke();
        }
        return new Void();
    }
}
