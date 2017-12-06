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

public class VideoFragment extends BaseFragment {

    public  static VideoFragment newInstance()
    {
        VideoFragment videoFragment=new VideoFragment();
         Bundle bundle=new Bundle();
        videoFragment.setArguments(bundle);

        return videoFragment;
    }


    @Override
    protected int getLayoutID() {
        return R.layout.fragment_video;
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
