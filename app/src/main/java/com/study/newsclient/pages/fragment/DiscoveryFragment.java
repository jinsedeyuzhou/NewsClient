package com.study.newsclient.pages.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.study.newsclient.R;
import com.study.newsclient.adpter.NewsChannelAdapter;
import com.study.newsclient.base.BaseFragment;
import com.study.newsclient.base.NewsListFragment;
import com.study.newsclient.bean.Channel;
import com.study.newsclient.view.CustomViewPager;
import com.yuxuan.common.util.DensityUtils;
import com.yuxuan.common.view.colortrackview.ColorTrackTabLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wyy on 2016/9/12.
 */
public class DiscoveryFragment extends BaseFragment {

    private View view;
    private ColorTrackTabLayout mColorTabLayout;
    private ImageView mCategory;
    private CustomViewPager mViewPager;
    private ArrayList<BaseFragment> mFragments;
    public ArrayList<Channel> mSelectedDatas = new ArrayList<>();
    private NewsChannelAdapter newsChannelAdapter;


    @Override
    protected int getLayoutID() {
        return R.layout.fragment_discovery;
    }

    @Override
    protected void initView(View view) {
        mColorTabLayout = (ColorTrackTabLayout) view.findViewById(R.id.color_tablayout);
        mCategory = (ImageView) view.findViewById(R.id.iv_category);
        mViewPager = (CustomViewPager) view.findViewById(R.id.custom_viewpager);
    }

    @Override
    protected void bindEvent() {


    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mSelectedDatas.add(new Channel(1,"新闻",1,20));
        mSelectedDatas.add(new Channel(2,"视频",2,20));
        mSelectedDatas.add(new Channel(3,"热点",3,20));
        mSelectedDatas.add(new Channel(4,"体育",4,20));
        mSelectedDatas.add(new Channel(5,"文化",5,20));
        mSelectedDatas.add(new Channel(6,"多媒体",6,20));
        mFragments = new ArrayList<>();
        for (int i=0;i<mSelectedDatas.size();i++)
        {
            NewsFragment fragment = NewsFragment.newInstance(mSelectedDatas.get(i).getId());
            mFragments.add(fragment);
        }

        newsChannelAdapter = new NewsChannelAdapter(getChildFragmentManager(),mFragments,mSelectedDatas);
        mViewPager.setAdapter(newsChannelAdapter);
        mViewPager.setOffscreenPageLimit(mSelectedDatas.size());
        mColorTabLayout.setTabPaddingLeftAndRight(DensityUtils.dp2px(getContext(),10), DensityUtils.dp2px(getContext(),10));
        mColorTabLayout.setupWithViewPager(mViewPager);
        mColorTabLayout.post(new Runnable() {
            @Override
            public void run() {
                //设置最小宽度，使其可以在滑动一部分距离
                ViewGroup slidingTabStrip = (ViewGroup) mColorTabLayout.getChildAt(0);
                slidingTabStrip.setMinimumWidth(slidingTabStrip.getMeasuredWidth() + mCategory.getMeasuredWidth());
            }
        });
        //隐藏指示器
        mColorTabLayout.setSelectedTabIndicatorHeight(0);
    }

    @Override
    protected void processClick(View v) {

    }
}
