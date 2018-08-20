package com.sharevar.appstudio.runtime.sdk.ui;

import com.sharevar.appstudio.data.Attr;
import com.sharevar.appstudio.data.Layout;
import com.sharevar.appstudio.data.Widget;
import com.sharevar.appstudio.object.function.Function;
import com.sharevar.appstudio.object.function.Parameter;
import com.sharevar.appstudio.runtime.sdk.FunctionAdapter;

import java.util.ArrayList;
import java.util.List;

public class LayoutAdapter extends FunctionAdapter {

    public LayoutAdapter(Function function) {
        super(function);
    }

    @Override
    public Object invoke() {
        Widget widget = new Widget();
        widget.setType(function.getTag());
        List<Attr> attrs = new ArrayList<>();
        for (Parameter parameter : function.getParameters()) {
            Attr attr = new Attr();
            attr.setName(parameter.getName());
            attr.setValue(parameter.getValue());
            attr.setType(parameter.getType().getName());
            attrs.add(attr);
        }
        widget.setAttrs(attrs);
        widget.setParentType(function.getParent());
        Layout layout = new Layout();
        layout.setWidget(widget);
        return layout;
    }
}
