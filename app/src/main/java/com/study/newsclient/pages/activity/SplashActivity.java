package com.study.newsclient.pages.activity;

import android.content.Intent;
import android.view.View;

import com.study.newsclient.R;
import com.study.newsclient.base.NewsActivity;

/**
 * Created by wyy on 2016/9/11.
 */
public class SplashActivity extends NewsActivity {
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
}
