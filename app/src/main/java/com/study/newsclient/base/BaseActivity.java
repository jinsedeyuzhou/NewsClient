package com.study.newsclient.base;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.yuxuan.common.base.CommonBaseActivity;
import com.yuxuan.common.util.AppManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public abstract class BaseActivity
        extends CommonBaseActivity {
    private static final String TAG = "BaseActivity";
    protected BaseApplication app;
    protected Context mContext;

    public abstract void initData();

    public abstract void initView();

    public boolean isAppOnForeground() {
        // Returns a list of application processes that are running on the
        // device

        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

    public boolean isScreenLocked() {

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = pm.isScreenOn();

        if (isScreenOn)
            return false;
        else
            return true;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case 0:

                break;

            default:
                break;
        }
        processClick(view);
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        // 软件盘模式
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //默认取消所有title，可使用自定义title。
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        this.mContext = this;
        this.app = ((BaseApplication) getApplication());
        initView();
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onStop() {
        super.onStop();


    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public abstract void processClick(View paramView);

    protected <E extends View> E F(@IdRes int viewId) {
        return (E) super.findViewById(viewId);
    }

    protected <E extends View> E F(@NonNull View view, @IdRes int viewId) {
        return (E) view.findViewById(viewId);
    }

    protected <E extends View> void C(@NonNull E view) {
        view.setOnClickListener(this);
    }

}
