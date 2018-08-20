package com.sharevar.appstudio.runtime.core;

import android.content.Context;

import com.sharevar.appstudio.object.function.CodeBlock;

public class RuntimeContext {
    private Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public CodeBlock getCodeBlock(){
        //todo
        return null;
    }
}
