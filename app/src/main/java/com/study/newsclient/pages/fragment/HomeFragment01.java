package com.study.newsclient.pages.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.study.newsclient.R;
import com.study.newsclient.adpter.NewsAdapters;
import com.study.newsclient.base.BaseFragment;
import com.study.newsclient.view.CustomViewPager;
import com.yuxuan.common.util.LogUtils;

import java.util.ArrayList;

public class HomeFragment01
        extends BaseFragment {
    private static final String TAG = HomeFragment01.class.getSimpleName();
    private static final int[] imgs = {R.drawable.tab_discovery, R.drawable.tab_message, R.drawable.tab_user};
    private ArrayList<BaseFragment> fragmentList;
    private TabLayout mTabLayout;
    private CustomViewPager mViewPager;
    private NewsAdapters newsAdapters;
    private ArrayList<String> tabTitles;

    private void initEvents() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            public void onTabReselected(TabLayout.Tab paramAnonymousTab) {
            }

            public void onTabSelected(TabLayout.Tab tab) {
                if (tab == mTabLayout.getTabAt(0)) {
                    mViewPager.setCurrentItem(0);
                } else if (tab == mTabLayout.getTabAt(1)) {
                    mViewPager.setCurrentItem(1);
                } else if (tab == mTabLayout.getTabAt(2)) {
                    mViewPager.setCurrentItem(2);
                }

            }

            public void onTabUnselected(TabLayout.Tab paramAnonymousTab) {
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int paramAnonymousInt) {
            }

            public void onPageScrolled(int paramAnonymousInt1, float paramAnonymousFloat, int paramAnonymousInt2) {
            }

            public void onPageSelected(int paramAnonymousInt) {
                LogUtils.v("HomeFragment", "position===" + paramAnonymousInt + "----" + mTabLayout.getSelectedTabPosition() + "===" + mTabLayout.getTabCount());
                if (paramAnonymousInt != mTabLayout.getSelectedTabPosition()) {
                    mTabLayout.getTabAt(paramAnonymousInt).select();
                }
            }
        });
    }

    private void initFragment() {
        tabTitles = new ArrayList();
        fragmentList = new ArrayList();
        tabTitles.add("发现");
        tabTitles.add("消息");
        tabTitles.add("账户");
        fragmentList.add(new DiscoveryFragment());
        fragmentList.add(new FeedFragment());
        fragmentList.add(new UserFragment());
        newsAdapters = new NewsAdapters(getContext(), getChildFragmentManager(), fragmentList, tabTitles, imgs);
        mViewPager.setAdapter(newsAdapters);
        mViewPager.setScrollable(false);
        mViewPager.setOffscreenPageLimit(0);
    }

    private void initTablayout() {
        mTabLayout.setupWithViewPager(mViewPager);
        int i = 0;
        while (i < mTabLayout.getTabCount()) {
            TabLayout.Tab localTab = mTabLayout.getTabAt(i);
            if (localTab != null) {
                localTab.setCustomView(newsAdapters.getTabView(i));
            }
            i++;
        }
        mTabLayout.getTabAt(0).getCustomView().setSelected(true);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
        mTabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        mViewPager = (CustomViewPager) view.findViewById(R.id.viewpager);
    }

    @Override
    protected void bindEvent() {


    }

    protected void initData(Bundle paramBundle) {
        initFragment();

        initTablayout();
        initEvents();

    }


    protected void processClick(View paramView) {
    }

}
