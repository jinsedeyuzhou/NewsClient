package com.study.newsclient.pages.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.study.newsclient.R;
import com.study.newsclient.base.BaseFragment;
import com.study.newsclient.view.CustomViewPager;
import com.yuxuan.common.view.colortrackview.ColorTrackTabLayout;


/**
 * Created by wyy on 2016/9/12.
 */
public class DiscoveryFragment extends BaseFragment {

    private View view;
    private ColorTrackTabLayout mColorTabLayout;
    private ImageView mCategory;
    private CustomViewPager mViewPager;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_discovery,container,false);
        mColorTabLayout = (ColorTrackTabLayout) view.findViewById(R.id.color_tablayout);
        mCategory = (ImageView) view.findViewById(R.id.iv_category);
        mViewPager = (CustomViewPager) view.findViewById(R.id.custom_viewpager);
        return view;
    }


    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void processClick(View v) {

    }
}
