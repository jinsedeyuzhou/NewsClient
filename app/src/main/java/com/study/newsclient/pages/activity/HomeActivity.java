package com.study.newsclient.pages.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.google.gson.reflect.TypeToken;
import com.study.newsclient.R;
import com.study.newsclient.base.BaseActivity;
import com.study.newsclient.bean.Channel;
import com.study.newsclient.database.BaseDao;
import com.study.newsclient.database.IDao;
import com.study.newsclient.net.CommonRequest;
import com.study.newsclient.pages.fragment.HomeFragment02;
import com.study.newsclient.restful.config.AppConfig;
import com.study.newsclient.restful.manager.RequestQueueManager;
import com.yuxuan.common.util.StatusBarUtils;

import java.util.ArrayList;

/**
 * Created by wyy on 2016/9/11.
 *
 */
public class HomeActivity extends BaseActivity {
//    private HomeFragment mHomeFragment;
    private HomeFragment02 mHomeFragment;
    private String curTag="home";
    private static boolean isExit = false;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (savedInstanceState==null)
        {
//            mHomeFragment = new HomeFragment();
            mHomeFragment = new HomeFragment02();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, mHomeFragment, curTag)
                    .commit();
        }
    }

    @Override
    protected void bindEvent() {

    }

    @Override
    public boolean isRegisterEvent() {
        return false;
    }

    @Override
    protected void onResume() {
//        Fragment fragment = getSupportFragmentManager().findFragmentByTag(
//                curTag);
//        if (fragment != null) {
//            fragment.onResume();
//        }
        super.onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Fragment fragment = getSupportFragmentManager().findFragmentByTag(
//                curTag);
//        if (fragment != null) {
//            fragment.onActivityResult(requestCode, resultCode, data);
//        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView() {

    }

    @Override
    public void processClick(View v) {

    }

    /**
     * 按两次退出
     */
    private void exit() {
        if (!isExit) {
            isExit = true;

            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


}
