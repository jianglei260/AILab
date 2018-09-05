package com.sharevar.appstudio;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class StudioApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
