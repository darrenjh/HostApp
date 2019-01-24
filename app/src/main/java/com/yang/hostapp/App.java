package com.yang.hostapp;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.didi.virtualapk.PluginManager;

/**
 * Created by yang on 2019/01/22.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        PluginManager.getInstance(base).init();
        ARouter.openLog();
        ARouter.openDebug();
        ARouter.init(this);
    }
}
