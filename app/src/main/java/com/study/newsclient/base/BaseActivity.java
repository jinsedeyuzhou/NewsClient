package com.study.newsclient.base;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.study.newsclient.utils.AppManager;
import com.study.newsclient.utils.LogUtils;

import java.util.List;

/**
 * Created by wyy on 2016/9/11.
 */
public abstract  class BaseActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "BaseActivity";

    private static final int ACTIVITY_RESUME = 0;
    private static final int ACTIVITY_STOP = 1;
    private static final int ACTIVITY_PAUSE = 2;
    private static final int ACTIVITY_DESTROY = 3;
    public int activityState;

    // 是否允许全屏
    private boolean mAllowFullScreen = true;
    // 是否跳转
    private boolean nojump;
    //重置状态
    private boolean resumeStatus;

    protected Context ct;
    protected NewsApplication app;
    public void setAllowFullScreen(boolean allowFullScreen) {
        this.mAllowFullScreen = allowFullScreen;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i(TAG, "onCreate");
        // 软件盘模式
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        if (mAllowFullScreen) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            if (mAllowFullScreen) {
                // 取消标题
                requestWindowFeature(Window.FEATURE_NO_TITLE);
            }

        }
        AppManager.getAppManager().addActivity(this);
        // 初始化
        ct = this;
        // 初始化
        app = (NewsApplication) getApplication();
        // 未知
        nojump = false;
        initViews();
        initData();
    }

    /**
     * 设置跳转
     */
    public void setnojump() {
        nojump = true;
    }




    /**
     * 判断是否锁屏
     */

    public boolean isScreenLocked() {

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = pm.isScreenOn();

        if (isScreenOn)
            return false;
        else
            return true;
    }

    /**
     * 程序是否在前台运行
     *
     * @return
     */
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

    public abstract void initViews();

    public abstract void initData();

    public abstract void processClick(View v);

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case 0:

                break;

            default:
                break;
        }
        processClick(v);
    }


    /**
     * 打印activity生命周期方法
     */
    protected void onStart() {
        super.onStart();
        activityState = ACTIVITY_STOP;
        LogUtils.i(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityState = ACTIVITY_RESUME;
        LogUtils.i(TAG, "onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        activityState = ACTIVITY_PAUSE;
        LogUtils.i(TAG, "onPause");

    }
    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.i(TAG, "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtils.i(TAG, "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityState = ACTIVITY_DESTROY;
        LogUtils.i(TAG, "onDestroy");
        AppManager.getAppManager().finishActivity(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.i(TAG, "onActivityResult");
    }

}
