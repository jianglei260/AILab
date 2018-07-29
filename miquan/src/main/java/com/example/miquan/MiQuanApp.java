package com.sharevar.miquan;

import android.app.Application;
import android.content.pm.ApplicationInfo;

import com.sharevar.miquan.data.server.RetrofitProvider;
import com.sharevar.miquan.upgrade.AppUpdateTool;
import com.facebook.drawee.backends.pipeline.Fresco;

public class MiQuanApp extends Application {
    private static MiQuanApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        instance = this;
        AppUpdateTool tool = new AppUpdateTool.Builder(this).setRequestUrl(RetrofitProvider.BASE_URl + "/v1/version").setAutoUpdate(true).build();
        tool.checkUpdate();
    }

    public static MiQuanApp getInstance() {
        return instance;
    }

    public boolean isDebugVersion() {
        return (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }
}
