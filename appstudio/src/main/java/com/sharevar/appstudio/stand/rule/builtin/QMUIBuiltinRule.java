package com.sharevar.appstudio.stand.rule.builtin;

import android.view.View;

public class QMUIBuiltinRule {
    public static class QMUIButtonRule extends ViewRule{

        public QMUIButtonRule(View view) {
            super(view);
        }
    }

    public static class QMUIAppBarLayout extends ViewGroupRule{

        public QMUIAppBarLayout(View view) {
            super(view);
        }
    }
}
