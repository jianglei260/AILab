package com.sharevar.appstudio.runtime.ui.stand.rule.builtin;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sharevar.appstudio.runtime.ui.stand.annotation.Option;
import com.sharevar.appstudio.runtime.ui.stand.annotation.WidgetAttr;

public class AndroidBuiltinRule {
    public static class ImageViewRule extends ViewRule {

        public ImageViewRule(View view) {
            super(view);
        }
    }

    public static class LinearLayoutRule extends ViewGroupRule {
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

    public static class RecylcerViewRule extends ViewGroupRule {
        public RecylcerViewRule(View view) {
            super(view);
        }
    }

    public static class RelativeLayoutRule extends ViewGroupRule {
        public RelativeLayoutRule(View view) {
            super(view);
        }
    }

    public static class SimpleDraweeViewRule extends ViewGroupRule {
        public SimpleDraweeViewRule(View view) {
            super(view);
        }
    }

    public static class TextViewRule extends ViewGroupRule {
        public TextViewRule(View view) {
            super(view);
        }

        @WidgetAttr(attr = "text", acceptType = "String", options = {})
        public ViewRule text(String value) {
            ((TextView) view).setText(value);
            return this;
        }
    }
}
