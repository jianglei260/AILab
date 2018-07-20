package com.example.appstudio.stand.rule.builtin;

import android.view.View;
import android.widget.ImageView;

import com.example.appstudio.stand.annotation.WidgetRule;

@WidgetRule(ImageView.class)
public class ImageViewRule extends ViewRule {
    public ImageViewRule(View view) {
        super(view);
    }
}
