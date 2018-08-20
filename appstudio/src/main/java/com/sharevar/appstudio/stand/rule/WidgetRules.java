package com.sharevar.appstudio.stand.rule;

import android.view.View;

import com.sharevar.appstudio.stand.annotation.WidgetAttr;
import com.sharevar.appstudio.stand.rule.builtin.ViewRule;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class WidgetRules {
    public static Class<? extends ViewRule> ruleClass(String type) {
        Class<? extends ViewRule> rule = lookFromBuiltin(type);
        Class<? extends View> superclass = null;
        while (rule == null && superclass != View.class) {
            try {
                superclass = (Class<? extends View>) Class.forName(type).getSuperclass();
                rule = lookFromBuiltin(superclass.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (rule == null) {
            rule = ViewRule.class;
        }
        return rule;
    }

    public static Class<? extends ViewRule> lookFromBuiltin(String type) {
        String simpleName = type;
        if (type.contains(".")) {
            try {
                simpleName = Class.forName(type).getSimpleName();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String packeageName = ViewRule.class.getName();
        int i = packeageName.lastIndexOf('.');
        if (i != -1) {
            packeageName = packeageName.substring(0, i);
        }
        String ruleClassName = packeageName + "." + simpleName + "Rule";
        Class<? extends ViewRule> ruleClass = null;
        try {
            ruleClass = (Class<? extends ViewRule>) Class.forName(ruleClassName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ruleClass;
    }

    public static Map<String, Method> layoutMethods(Class<? extends ViewRule> ruleClass) {
        Map<String, Method> attrs = new HashMap<>();
        putMethods(ruleClass, attrs);
        while (ruleClass != ViewRule.class) {
            ruleClass = (Class<? extends ViewRule>) ruleClass.getSuperclass();
            putMethods(ruleClass, attrs);
        }
        return attrs;
    }

    public static void putMethods(Class ruleClass, Map<String, Method> attrs) {
        Method[] methods = ruleClass.getDeclaredMethods();
        for (Method method : methods) {
            WidgetAttr widgetAttr = method.getAnnotation(WidgetAttr.class);
            if (widgetAttr != null) {
                String attr = widgetAttr.attr();
                attrs.put(attr, method);
            }
        }
    }
}
