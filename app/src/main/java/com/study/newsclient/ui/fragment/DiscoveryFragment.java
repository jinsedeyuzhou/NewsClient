package com.study.newsclient.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.study.newsclient.R;
import com.study.newsclient.base.BaseFragment;

/**
 * Created by wyy on 2016/9/12.
 */
public class DiscoveryFragment extends BaseFragment {
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view=inflater.inflate(R.layout.fragment_discovery,container,false);
        return view;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void processClick(View v) {

    }
}
