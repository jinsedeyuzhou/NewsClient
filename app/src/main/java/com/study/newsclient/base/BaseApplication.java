package com.study.newsclient.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;


import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.ebrightmoon.retrofitrx.retrofit.AppClient;
import com.study.newsclient.database.DatabaseHelper;
import com.study.newsclient.restful.manager.RequestQueueManager;
//import com.tencent.bugly.crashreport.CrashReport;
import com.yuxuan.common.base.CommonApplication;
import com.yuxuan.common.util.LogUtils;
import com.yuxuan.common.widget.imageloader.LoaderFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by wyy on 2016/9/11.
 */
public class BaseApplication extends Application {
    private static BaseApplication app;

    public static BaseApplication getApp() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        CommonApplication.initQDApp(this);
        LoaderFactory.getLoader().init(this);
        RequestQueueManager.getInstance(this).getRequestQueue();
        //初始化数据库
        DatabaseHelper.getHelper(this);
//        initCrashReport();
        LogUtils.setShowLog(true);

    }


    private void initCrashReport() {
//        Context context = getApplicationContext();
//        // 获取当前包名
//        String packageName = context.getPackageName();
//        // 获取当前进程名
//        String processName = getProcessName(android.os.Process.myPid());
//        // 设置是否为上报进程
//        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
//        strategy.setUploadProcess(processName == null || processName.equals(packageName));
//        // 初始化Bugly isDebug 如果为true,开启调试模式，会即使上传错误消息，如果上线，给为false
//        CrashReport.initCrashReport(context, "6e5615680b", true, strategy);
//        // 如果通过“AndroidManifest.xml”来配置APP信息，初始化方法如下
//        // CrashReport.initCrashReport(context, strategy);
    }


    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        //支持超过65535
        MultiDex.install(this);
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

}
