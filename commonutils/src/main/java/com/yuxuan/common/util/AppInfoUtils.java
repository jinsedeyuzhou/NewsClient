package com.yuxuan.common.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import java.util.List;

/**
 * Created by wyy on 2017/4/9.
 * 获取系统相关信息
 */

public class AppInfoUtils {

    private AppInfoUtils()
    {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");

    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context)
    {
        try
        {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context)
    {
        try
        {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回版本号
     * 对应build.gradle中的versionCode
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取ManifestMetaData 中meta中的值
     *
     * @param appContext
     * @param metaKey
     * @return
     */
    public static String getManifestMetaData(Context appContext, String metaKey) {
        try {
            ApplicationInfo appi = appContext.getPackageManager()
                    .getApplicationInfo(appContext.getPackageName(),
                            PackageManager.GET_META_DATA);
            Bundle bundle = appi.metaData;
            Object value = bundle.get(metaKey);
            return String.valueOf(value);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    /**
     * 获取当前显示的activity的ClassName
     *
     * @param mContext
     * @return
     */
    public static String getTopActivityName(Context mContext) {
        String activityName = null;
        ActivityManager manager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> appTasks = manager.getRunningTasks(1);
        if (!appTasks.isEmpty()) {
            ComponentName topActivity = appTasks.get(0).topActivity;
            activityName = topActivity.getClassName();
        }
        return activityName;
    }

    /**
     * 判断activity是否在栈顶
     *
     * @param mContext
     * @param simpleClassName
     * @return
     */
    public static boolean isRunningForeground(Context mContext, String simpleClassName) {
        String packageName = mContext.getPackageName();
        String topActivityName = getTopActivityName(mContext);
        if (packageName != null && topActivityName != null) {
            return topActivityName.endsWith(simpleClassName);
        } else {
            return false;
        }
    }

    public static String getAppSource(Context appContext) {
        return getManifestMetaData(appContext, "UMENG_CHANNEL");
    }

    /**
     * 获取当前应用的包名
     *
     * @param pContext
     * @return
     */
    public static String getAppPackageName(Context pContext) {
        PackageInfo info = null;
        String packageName = null;
        try {
            info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), 0);
            packageName = info.packageName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageName;
    }

}
