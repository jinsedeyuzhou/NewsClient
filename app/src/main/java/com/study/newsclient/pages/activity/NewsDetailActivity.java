package com.study.newsclient.pages.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.study.newsclient.R;
import com.study.newsclient.base.BaseActivity;

public class NewsDetailActivity
        extends BaseActivity {
    private Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_news_detail);
    }

    public void initData() {
    }

    public void initView() {

        mToolBar = ((Toolbar) findViewById(R.id.toolbar_detail));
        findViewById(R.id.btn_skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewsDetailActivity.this, DetailActivity.class));
            }
        });
    }

    @Override
    protected void bindEvent() {

    }

    public void processClick(View paramView) {
    }

    @Override
    public boolean isRegisterEvent() {
        return false;
    }
}
