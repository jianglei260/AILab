package com.sharevar.appstudio.common.serializable;

import android.util.Xml;

import com.sharevar.appstudio.common.ds.CollectionOP;
import com.sharevar.appstudio.common.object.ObjectTool;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class XmlUtil {
    public static <T> List<T> listFrom(InputStreamReader reader, Class... beans) {
        List list = new ArrayList<>();
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(reader);
            int event = parser.getEventType();
            Stack<Object> stack = new Stack<>();
            List childList = null;
            while (event != XmlPullParser.END_DOCUMENT) {
                switch (event) {
                    case XmlPullParser.START_TAG:
                        String name = parser.getName();
                        Class bean = CollectionOP.findByAttr(beans, "simpleName", name);
                        if (bean != null) {
                            Object object = bean.newInstance();
                            if (bean.equals(beans[0])) {
                                list.add(bean);
                            } else {
                                childList = getList(stack.peek());
                                childList.add(bean);
                            }
                            int count = parser.getAttributeCount();
                            for (int i = 0; i < count; i++) {
                                String attrName = parser.getAttributeName(i);
                                Method setMethod = ObjectTool.setMethod(bean, attrName);
                                setMethod.invoke(object, parser.getAttributeValue(i));
                            }
                            stack.push(object);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (stack.peek().getClass().getSimpleName().equals(parser.getName())) {
                            stack.pop();
                        }
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List getList(Object object) {
        Method[] methods = object.getClass().getMethods();
        for (Method method : methods) {
            if (method.getReturnType().equals(List.class)) {
                try {
                    return (List) method.invoke(object);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}