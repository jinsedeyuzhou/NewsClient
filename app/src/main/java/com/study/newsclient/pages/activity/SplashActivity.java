package com.study.newsclient.pages.activity;

import android.content.Intent;
import android.view.View;

import com.study.newsclient.R;
import com.study.newsclient.base.BaseActivity;

/**
 * Created by wyy on 2016/9/11.
 */
public class SplashActivity extends BaseActivity {
    @Override
    public void initView() {
        setContentView(R.layout.activity_guide);
        Intent intent=new Intent(mContext,HomeActivity.class);
        startActivity(intent);
        SplashActivity.this.finish();
    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {

    }

    @Override
    public boolean isRegisterEvent() {
        return false;
    }
}
