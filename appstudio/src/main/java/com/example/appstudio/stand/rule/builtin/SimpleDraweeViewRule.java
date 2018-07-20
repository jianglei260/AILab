package com.example.appstudio.stand.rule.builtin;

import android.view.View;

import com.example.appstudio.stand.annotation.WidgetRule;
import com.facebook.drawee.view.SimpleDraweeView;

@WidgetRule(SimpleDraweeView.class)
public class SimpleDraweeViewRule extends ImageViewRule {
    public SimpleDraweeViewRule(View view) {
        super(view);
    }
}
