package com.sharevar.appstudio.stand.rule.builtin;

import android.view.View;
import android.widget.RelativeLayout;

import com.sharevar.appstudio.stand.annotation.WidgetRule;

@WidgetRule(RelativeLayout.class)
public class RelativeLayoutRule extends ViewGroupRule {
    public RelativeLayoutRule(View view) {
        super(view);
    }
}
