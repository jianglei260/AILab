package com.sharevar.appstudio.runtime.core;

import android.content.Context;

import com.sharevar.appstudio.runtime.core.function.CodeBlock;
import com.sharevar.appstudio.runtime.core.function.Function;

public class RuntimeContext {
    private Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public CodeBlock getIfCodeBlock(Function function){
        //todo
        return null;
    }
    public CodeBlock getElseCodeBlock(Function function){
        //todo
        return null;
    }
}
