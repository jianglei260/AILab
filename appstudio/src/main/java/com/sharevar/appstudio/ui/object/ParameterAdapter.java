package com.sharevar.appstudio.ui.object;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.sharevar.appstudio.runtime.core.function.Parameter;

public abstract class ParameterAdapter {
    protected Parameter parameter;
    protected Context context;

    public ParameterAdapter(Parameter parameter) {
        this.parameter = parameter;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public abstract View getView();
    public abstract Parameter getParameter();
    public abstract TextView nameText();
}
