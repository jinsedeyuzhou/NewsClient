package com.study.newsclient.ui.activity;

import android.view.View;
import android.widget.Toolbar;

import com.study.newsclient.R;
import com.study.newsclient.base.NewsActivity;

public class DetailActivity
        extends NewsActivity
{
    private Toolbar mToolBar;

    public void initData() {}

    public void initView()
    {
        setContentView(R.layout.activity_detail);
        this.mToolBar = ((Toolbar)findViewById(R.id.toolbar_detail));
    }

    public void processClick(View paramView) {}
}
