package com.sharevar.appstudio.ui.object;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sharevar.appstudio.R;
import com.sharevar.appstudio.runtime.core.function.Parameter;

public class DefaultParameterAdapter extends ParameterAdapter {
    TextView nameText;
    EditText valueEditText;
    public DefaultParameterAdapter(Parameter parameter) {
        super(parameter);
    }

    @Override
    public View getView() {
        View root= LayoutInflater.from(context).inflate(R.layout.layout_parameter_default,null,true);
        nameText=root.findViewById(R.id.param_name);
        valueEditText=root.findViewById(R.id.param_value);
        nameText.setText(parameter.getName());
        valueEditText.setHint(parameter.getType().getName());
        return root;
    }

    @Override
    public Parameter getParameter() {
        return parameter;
    }

    @Override
    public TextView nameText() {
        return nameText;
    }
}
