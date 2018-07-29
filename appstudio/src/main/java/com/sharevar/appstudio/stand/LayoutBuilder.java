package com.sharevar.appstudio.stand;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sharevar.appstudio.data.Attr;
import com.sharevar.appstudio.data.Layout;
import com.sharevar.appstudio.data.Widget;
import com.sharevar.appstudio.stand.annotation.WidgetRule;
import com.sharevar.appstudio.stand.databinding.Bindable;
import com.sharevar.appstudio.stand.rule.WidgetRules;
import com.sharevar.appstudio.stand.rule.builtin.ViewGroupRule;
import com.sharevar.appstudio.stand.rule.builtin.ViewRule;

import org.xmlpull.v1.XmlPullParser;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LayoutBuilder {
    private static final String TAG = "LayoutBuilder";
    private static Map<String, Class<? extends ViewRule>> ruleMap = new HashMap<>();

    public static Layout from(Context context, @LayoutRes int layoutId) {
        Resources res = context.getResources();
        XmlResourceParser parser = res.getLayout(layoutId);
        final AttributeSet attrs = Xml.asAttributeSet(parser);
        try {
            int type;
            while ((type = parser.next()) != XmlPullParser.START_TAG &&
                    type != XmlPullParser.END_DOCUMENT) {
                // Empty
            }
            Layout root = inflateLayout(parser, null, XmlPullParser.START_TAG);
            return root;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Layout generateLayout(XmlPullParser parser) {
        String name = parser.getName();
        Layout layout = new Layout();
        Widget widget = new Widget();
        if (!name.contains(".")) {
            if (name.equals("View")) {
                name = "android.view.View";
            } else {
                name = "android.widget." + name;
            }
        }
        widget.setType(name);
        layout.setWidget(widget);
        layout.setBindAttrs(parseAttrs(parser));
        return layout;
    }

    private static Layout inflateLayout(XmlPullParser parser, Layout parent, int lastType) {
        int type;
        Layout layout = generateLayout(parser);
        try {
            while ((type = parser.next()) != XmlPullParser.END_DOCUMENT) {
                if (type == XmlPullParser.START_TAG) {
                    if (lastType == XmlPullParser.START_TAG) {
                        inflateLayout(parser, layout, XmlPullParser.START_TAG);
                    }
                } else {
                    if (parent != null)
                        parent.getChildren().add(layout);
                    return layout;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return layout;
    }

    public static List<Attr> parseAttrs(XmlPullParser parser) {
        List<Attr> attrs = new ArrayList<>();
        for (int i = 0; i < parser.getAttributeCount(); i++) {
            Attr attr = new Attr();
            attr.setName(parser.getAttributeName(i));
            attr.setValue(parser.getAttributeValue(i));
            attrs.add(attr);
        }
        return attrs;
    }

    public static View buildView(Layout layout, Context context) {
        String type = layout.getWidget().getType();
        Class<? extends ViewRule> ruleClass = WidgetRules.ruleClass(type);
        Class<? extends View> viewClass = ruleClass.getAnnotation(WidgetRule.class).value();
        try {
            View view = createView(layout, context, ruleClass, viewClass);
            List<Layout> children = layout.getChildren();
            if ((view instanceof ViewGroup) && children != null && children.size() > 0) {
                for (Layout child : children) {
                    buildChildren(child, context, (ViewGroup) view, ruleClass);
                }
            }
            return view;
        } catch (Exception e) {
            e.printStackTrace();
        }
        TextView errorTextView = new TextView(context);
        errorTextView.setText("build view error!!!");
        return errorTextView;
    }

    public static View createView(Layout layout, Context context, Class<? extends ViewRule> ruleClass, Class<? extends View> viewClass) {
        try {
            View root = viewClass.getConstructor(Context.class).newInstance(context);
            ViewRule rule = ruleClass.getConstructor(View.class).newInstance(root);
            Map<String, Method> layoutMethods = WidgetRules.layoutMethods(ruleClass);
            List<Attr> attrs = layout.getBindAttrs();
            for (Attr attr : attrs) {
                if (handleAttrValue(attr, root, rule)) {
                    continue;
                }
                Method method = layoutMethods.get(attr.getName());
                if (method != null) {
                    method.invoke(rule, attr.getValue());
                }
            }
            return root;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean handleAttrValue(Attr attr, View view, ViewRule rule) {
        if (attr.getValue() instanceof Bindable) {
            Object result = ((Bindable) attr.getValue()).invoke(view, attr, rule);
            return true;
        }
        return false;
    }

    public static void buildChildren(Layout layout, Context context, ViewGroup parentView, Class<? extends ViewRule> parentRuleClass) {
        String type = layout.getWidget().getType();
        Class<? extends ViewRule> ruleClass = WidgetRules.ruleClass(type);
        Class<? extends View> viewClass = ruleClass.getAnnotation(WidgetRule.class).value();
        try {
            View view = createView(layout, context, ruleClass, viewClass);
            ViewGroupRule parentRule = (ViewGroupRule) parentRuleClass.getConstructor(View.class).newInstance(parentView);
            ViewGroup.LayoutParams layoutParams = parentRule.generateLayoutParams(layout.getBindAttrs(), view);
            parentView.addView(view, layoutParams);
            List<Layout> children = layout.getChildren();
            if ((view instanceof ViewGroup) && children != null && children.size() > 0) {
                for (Layout child : children) {
                    buildChildren(child, context, (ViewGroup) view, ruleClass);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String toXMLString(Layout layout) {
        //todo
        StringBuilder builder = new StringBuilder();
        return builder.toString();
    }
}
