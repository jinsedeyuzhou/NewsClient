package com.study.newsclient.pages.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;

import com.study.newsclient.R;
import com.study.newsclient.base.BaseActivity;

public class DetailActivity
        extends BaseActivity
{
    private Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_detail);
    }

    public void initData() {}

    public void initView()
    {

        this.mToolBar = ((Toolbar)findViewById(R.id.toolbar_detail));
    }

    @Override
    protected void bindEvent() {

    }

    public void processClick(View paramView) {}

    @Override
    public boolean isRegisterEvent() {
        return false;
    }
}
