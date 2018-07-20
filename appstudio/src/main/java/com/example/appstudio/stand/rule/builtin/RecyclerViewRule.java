package com.example.appstudio.stand.rule.builtin;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.appstudio.stand.annotation.WidgetRule;

@WidgetRule(RecyclerView.class)
public class RecyclerViewRule extends ViewGroupRule {
    public RecyclerViewRule(View view) {
        super(view);
    }
}
