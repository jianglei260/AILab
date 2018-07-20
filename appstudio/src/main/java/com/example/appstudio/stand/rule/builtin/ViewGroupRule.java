package com.example.appstudio.stand.rule.builtin;

import android.view.View;
import android.view.ViewGroup;

import com.example.appstudio.data.Attr;
import com.example.appstudio.stand.annotation.WidgetRule;
import com.example.appstudio.stand.rule.WidgetRules;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@WidgetRule(ViewGroup.class)
public class ViewGroupRule extends ViewRule {
    public ViewGroupRule(View view) {
        super(view);
    }

    public ViewGroup.LayoutParams generateLayoutParams(List<Attr> attrs, View child) {
        ViewGroup.LayoutParams childParams = child.getLayoutParams();
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (childParams != null)
            layoutParams = new ViewGroup.LayoutParams(childParams);
        Map<String, Method> layoutMethods = WidgetRules.layoutMethods(getClass());
        try {
            for (Attr attr : attrs) {
                if (attr.getName().startsWith("layout_")) {
                    Method method = layoutMethods.get(attr.getName());
                    if (method != null) {
                        method.invoke(this, attr.getValue());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return layoutParams;
    }
}
