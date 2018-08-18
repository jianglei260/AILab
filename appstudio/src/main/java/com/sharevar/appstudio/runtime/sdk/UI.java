package com.sharevar.appstudio.runtime.sdk;

import android.view.View;

import com.sharevar.appstudio.R;
import com.sharevar.appstudio.object.function.Function;
import com.sharevar.appstudio.runtime.annotation.Name;

@Name(id = R.string.ui)
public class UI {

    public static class Widget extends Function {
        public static Widget get() {
            Widget widget = new Widget();
            Package pa;
            return widget;
        }

        @Override
        public Object invoke() {
            return super.invoke();
        }
    }

    @Name(id = R.string.linear_layout)
    public static Widget linearLayout() {
        View
        return Widget.get();
    }

    @Name(id = R.string.text_view)
    public static Widget textView() {
        return Widget.get();
    }

}
