package com.example.appstudio.stand.rule.builtin;

import android.view.View;
import android.widget.TextView;

import com.example.appstudio.stand.annotation.WidgetAttr;
import com.example.appstudio.stand.annotation.WidgetRule;

@WidgetRule(TextView.class)
public class TextViewRule extends ViewRule {
    public TextViewRule(View view) {
        super(view);
    }

    @WidgetAttr(attr = "text", acceptType = "String", options = {})
    public ViewRule layoutWidth(String value) {
        ((TextView) view).setText(value);
        return this;
    }
}
