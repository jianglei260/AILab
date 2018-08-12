package com.sharevar.appstudio.ui.object;

import android.view.View;
import android.widget.TextView;

import com.sharevar.appstudio.object.function.Parameter;

public abstract class ParameterAdapter {
    public abstract View getView();
    public abstract Parameter getParameter();
    public abstract TextView nameText();
}
