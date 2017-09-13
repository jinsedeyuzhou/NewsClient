package com.study.newsclient.pages.activity;

import android.view.View;
import android.widget.Toolbar;

import com.study.newsclient.R;
import com.study.newsclient.base.BaseActivity;

public class DetailActivity
        extends BaseActivity
{
    private Toolbar mToolBar;

    public void initData() {}

    public void initView()
    {
        setContentView(R.layout.activity_detail);
        this.mToolBar = ((Toolbar)findViewById(R.id.toolbar_detail));
    }

    public void processClick(View paramView) {}

    @Override
    public boolean isRegisterEvent() {
        return false;
    }
}
