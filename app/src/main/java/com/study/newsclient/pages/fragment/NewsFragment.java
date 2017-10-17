package com.study.newsclient.pages.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.study.newsclient.R;
import com.study.newsclient.base.BaseFragment;

/**
 * Created by wyy on 2017/9/4.
 */

public class NewsFragment extends BaseFragment {

    public static NewsFragment newInstance() {
        NewsFragment newsFragment = new NewsFragment();

        Bundle bundle = new Bundle();
        newsFragment.setArguments(bundle);

        return newsFragment;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initView(View view) {

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
