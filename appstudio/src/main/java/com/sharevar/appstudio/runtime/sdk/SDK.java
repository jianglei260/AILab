package com.sharevar.appstudio.runtime.sdk;

import android.content.Context;
import android.content.res.XmlResourceParser;

import com.sharevar.appstudio.runtime.core.RuntimeContext;

import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;

public class SDK {
    private static SDK instance;
    private RuntimeContext context;

    public static void init(RuntimeContext context) {
        instance = new SDK(context);
    }

    public SDK(RuntimeContext context) {
        this.context = context;
    }

    public void loadFuction() {
        try {
            XmlResourceParser parser = context.getContext().getResources().getAssets().openXmlResourceParser("function.xml");
            parser.getName();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
