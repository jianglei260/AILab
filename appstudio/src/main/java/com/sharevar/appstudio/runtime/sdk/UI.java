package com.sharevar.appstudio.runtime.sdk;

import android.view.View;

import com.sharevar.appstudio.R;
import com.sharevar.appstudio.object.function.Function;
import com.sharevar.appstudio.runtime.annotation.Name;

@Name(id = R.string.ui)
public class UI {

    @Name(id = R.string.ui)
    public static class Widget extends Function {
        Widget() {

        }

        public static Widget get() {
            Widget widget = new Widget();
            return widget;
        }

        @Override
        public Object invoke() {
            return super.invoke();
        }
    }

    @Name(id = R.string.linear_layout)
    public static Widget linearLayout() {
        return Widget.get();
    }

    @Name(id = R.string.text_view)
    public static Widget textView() {
        return Widget.get();
    }

    public Function childView(){
        return new Function(){};
    }
    public Function addView() {
        return new Function() {
            @Override
            public Object invoke() {
                return super.invoke();
            }
        };
    }
}
