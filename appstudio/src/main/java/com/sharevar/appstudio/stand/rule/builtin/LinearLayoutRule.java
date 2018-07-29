package com.sharevar.appstudio.stand.rule.builtin;

import android.view.View;
import android.widget.LinearLayout;

import com.sharevar.appstudio.stand.annotation.Option;
import com.sharevar.appstudio.stand.annotation.WidgetAttr;
import com.sharevar.appstudio.stand.annotation.WidgetRule;

@WidgetRule(LinearLayout.class)
public class LinearLayoutRule extends ViewGroupRule {
    public LinearLayoutRule(View view) {
        super(view);
    }

    @WidgetAttr(attr = "orientation", acceptType = "Int", options = {@Option(display = "vertical",
            value = LinearLayout.VERTICAL), @Option(display = "horizontal", value = LinearLayout.HORIZONTAL)})
    public ViewRule orientation(String value) {
        ((LinearLayout) view).setOrientation(Integer.valueOf(value));
        return this;
    }

}
