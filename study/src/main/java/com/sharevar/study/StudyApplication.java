package com.sharevar.study;

import android.app.Application;
import android.content.pm.ApplicationInfo;

import com.facebook.drawee.backends.pipeline.Fresco;

public class StudyApplication extends Application {

    private static StudyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        instance = this;
    }

    public static StudyApplication getInstance() {
        return instance;
    }

    public boolean isDebugVersion() {
        return (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }
}
