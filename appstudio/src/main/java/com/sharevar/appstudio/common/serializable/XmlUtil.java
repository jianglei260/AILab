package com.sharevar.appstudio.common.serializable;

import android.util.Xml;

import com.sharevar.appstudio.common.ds.CollectionOP;
import com.sharevar.appstudio.common.object.ObjectTool;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class XmlUtil {
    public static <T> List<T> listFrom(InputStreamReader reader, Class... beans) {
        List list = new ArrayList<>();
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(reader);
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                switch (event) {
                    case XmlPullParser.START_TAG:
                        String name = parser.getName();
                        Class bean = CollectionOP.findByAttr(beans, "simpleName", name);
                        if (bean != null) {
                            Object object = bean.newInstance();
                            int count = parser.getAttributeCount();
                            for (int i = 0; i < count; i++) {
                                String attrName = parser.getAttributeName(i);
                                Method setMethod=ObjectTool.setMethod(bean,attrName);
                                setMethod.invoke(object,parser.getAttributeValue(i));
                            }
                        }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
