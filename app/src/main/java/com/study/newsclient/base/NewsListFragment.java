package com.study.newsclient.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.study.newsclient.R;
import com.yuxuan.common.base.CommonBaseFragment;

public abstract class NewsListFragment
        extends CommonBaseFragment
{
    protected void initData(Bundle paramBundle) {}

    protected View initView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup)
    {
        return paramLayoutInflater.inflate(R.layout.fragment_container, paramViewGroup, false);
    }

    protected void processClick(View paramView) {}
}
