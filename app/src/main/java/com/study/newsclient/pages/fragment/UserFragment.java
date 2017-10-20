package com.study.newsclient.pages.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.study.newsclient.R;
import com.study.newsclient.base.BaseFragment;
import com.study.newsclient.pages.activity.DetailActivity;

/**
 * Created by wyy on 2016/9/17.
 */
public class UserFragment extends BaseFragment {

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initView(View view) {
        view.findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DetailActivity.class));
            }
        });
    }

    @Override
    protected void bindEvent() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void processClick(View v) {

    }
}
