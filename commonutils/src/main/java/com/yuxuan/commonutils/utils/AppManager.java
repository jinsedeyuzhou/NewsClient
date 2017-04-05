package com.yuxuan.commonutils.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

/**
 * Created by wyy on 2016/9/11.
 * 应用程序Activity管理类，用于Activity管理和应用程序退出
 */
public class AppManager {

    //创建activity栈
    private static Stack<Activity> activityStack;
    private final static AppManager instance = new AppManager();

    /**
     * 使用单利模式
     */
    private AppManager() {
    }

    public static AppManager getAppManager() {
        return instance;
    }

    /**
     * 添加activity到栈
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (null == activityStack) {
            activityStack = new Stack<Activity>();
        }
    }

    /**
     * 获取当前Activity(栈顶activity）
     *
     * @return
     */
    public Activity currentActivity() {
        if (null == activityStack || activityStack.empty()) {
            return null;
        }
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 获取Activity,没有找到返回null
     *
     * @param cls
     * @return
     */
    public Activity findActivity(Class<?> cls) {
        Activity activity = null;
        for (Activity aty : activityStack) {
            if (aty.getClass().equals(cls)) {
                activity = aty;
                break;
            }
        }
        return activity;
    }

    /**
     * 结束制定的Activity
     *
     * @param activity
     */
    public void finishActivity(Activity activity) {
        if (null != activity) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束当前Activity
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束制定的Activity
     *
     * @param cls
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls))
                finishActivity(activity);
        }
    }

    /**
     * 结束除了指定activity以外的全部activity，如果cls不存在，则栈全部清空
     * @param cls
     */
    public void finishOthersActivity(Class<?> cls) {

        for (Activity activity : activityStack) {
            if (!activity.getClass().equals(cls))
                finishActivity(activity);
        }

    }

    /**
     * 结束所有的Activity
     */
    public void finishAllActivity()
    {
        for (int i=0,size=activityStack.size();i<size;i++)
        {
            if (null!=activityStack.get(i))
            {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 应用程序退出
     * @param context
     */
    public void AppExit(Context context)
    {
        try {
            finishActivity();
            ActivityManager manager= (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            manager.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        }
        catch (Exception e)
        {
            System.exit(0);
        }
    }
}
