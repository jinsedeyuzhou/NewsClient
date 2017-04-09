package com.study.newsclient.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.study.newsclient.base.CrashHandler;

/**
 * Created by wyy on 2016/9/11.
 */
public class NewsApplication extends Application {
    private static NewsApplication app;

    public static NewsApplication getApp() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        //支持超过65535
        MultiDex.install(this);
    }




}
