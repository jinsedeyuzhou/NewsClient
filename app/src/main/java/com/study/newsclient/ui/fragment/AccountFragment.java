package com.study.newsclient.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.ViewUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.study.newsclient.R;
import com.study.newsclient.base.BaseFragment;

/**
 * Created by wyy on 2016/9/17.
 */
public class AccountFragment extends BaseFragment{

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container) {
        View view=inflater.inflate(R.layout.fragment_account,container,false);

        return view;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void processClick(View v) {

    }
}
