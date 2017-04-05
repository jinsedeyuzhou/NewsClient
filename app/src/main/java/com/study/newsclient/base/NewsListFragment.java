package com.study.newsclient.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.study.newsclient.R;
import com.yuxuan.commonutils.base.BaseFragment;

public abstract class NewsListFragment
        extends BaseFragment
{
    protected void initData(Bundle paramBundle) {}

    protected View initView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup)
    {
        return paramLayoutInflater.inflate(R.layout.fragment_container, paramViewGroup, false);
    }

    protected void processClick(View paramView) {}
}
