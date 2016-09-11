package com.study.newsclient.base;

import android.app.Application;

/**
 * Created by wyy on 2016/9/11.
 */
public class NewsApplication extends Application {
    private static  NewsApplication app;

    public static NewsApplication getApp()
    {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }
}
