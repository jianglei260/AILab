package com.sharevar.appstudio.stand.rule.builtin;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.sharevar.appstudio.stand.annotation.Option;
import com.sharevar.appstudio.stand.annotation.WidgetAttr;
import com.sharevar.appstudio.stand.annotation.WidgetRule;
import com.sharevar.appstudio.stand.type.Type;
import com.sharevar.appstudio.stand.type.TypeInference;

import java.util.ArrayList;

@WidgetRule(View.class)
public class ViewRule {
    protected View view;
    protected ViewGroup.LayoutParams params;

    public int dip(int value) {
        Context context = view.getContext();
        return (int) (value * context.getResources().getDisplayMetrics().density);
    }

    public ViewRule(View view) {
        this.view = view;
        params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public int toInt(String value) {
        return Integer.valueOf(value);
    }

    @WidgetAttr(attr = "layout_width", acceptType = "Int", options = {@Option(display = "wrap_content",
            value = ViewGroup.LayoutParams.WRAP_CONTENT), @Option(display = "match_parent", value = ViewGroup.LayoutParams.MATCH_PARENT)})
    public ViewRule layoutWidth(String value) {
        params.width = toType(Integer.class, value);
        return this;
    }

    @WidgetAttr(attr = "layout_height", acceptType = "Int", options = {@Option(display = "wrap_content",
            value = ViewGroup.LayoutParams.WRAP_CONTENT), @Option(display = "match_parent", value = ViewGroup.LayoutParams.MATCH_PARENT)})
    public ViewRule layoutHeight(String value) {
        params.width = toType(Integer.class, value);
        return this;
    }

    @WidgetAttr(attr = "padding", acceptType = "Int", options = {@Option(display = "0dp", value = 0), @Option(display = "4dp", value = 4), @Option(display = "8dp", value = 8)})
    public ViewRule padding(String value) {
        int padding = dip(toType(Integer.class, value));
        view.setPadding(padding, padding, padding, padding);
        return this;
    }


    public static <T> T toType(Class<T> toType, Object value) {
        return (T) TypeInference.get().convertTo(value, Type.of(toType));
    }
}
