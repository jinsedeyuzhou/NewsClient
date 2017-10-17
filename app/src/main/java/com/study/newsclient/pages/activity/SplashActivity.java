package com.study.newsclient.pages.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.study.newsclient.R;
import com.study.newsclient.base.BaseActivity;
import com.yuxuan.common.permission.OnPermissionCallback;
import com.yuxuan.common.permission.PermissionManager;

/**
 * Created by wyy on 2016/9/11.
 */
public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_guide);
    }

    @Override
    public void initView() {

        Intent intent = new Intent(mContext, HomeActivity.class);
        startActivity(intent);
        SplashActivity.this.finish();
    }

    @Override
    protected void bindEvent() {

    }

    @Override
    public void initData() {
        PermissionManager.instance().with(this).request(new OnPermissionCallback() {
            @Override
            public void onRequestAllow(String permissionName) {
            }

            @Override
            public void onRequestRefuse(String permissionName) {
            }

            @Override
            public void onRequestNoAsk(String permissionName) {
            }
        }, Manifest.permission.CALL_PHONE);

    }

    @Override
    public void processClick(View v) {

    }

    @Override
    public boolean isRegisterEvent() {
        return false;
    }
}
