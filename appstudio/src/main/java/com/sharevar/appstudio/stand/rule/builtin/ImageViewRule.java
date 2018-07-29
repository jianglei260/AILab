package com.sharevar.appstudio.stand.rule.builtin;

import android.view.View;
import android.widget.ImageView;

import com.sharevar.appstudio.stand.annotation.WidgetRule;

@WidgetRule(ImageView.class)
public class ImageViewRule extends ViewRule {
    public ImageViewRule(View view) {
        super(view);
    }
}
