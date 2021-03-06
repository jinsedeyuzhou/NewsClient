package com.study.newsclient.pages.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;

import com.study.newsclient.R;
import com.study.newsclient.adapter.NewsAdapter;
import com.study.newsclient.base.BaseFragment;
import com.study.newsclient.view.CustomViewPager;

import java.util.ArrayList;

/**
 * Created by wyy on 2016/9/17.
 */
public class HomeFragment extends BaseFragment {
    private static final String TAG=HomeFragment.class.getSimpleName();
    private SparseArray<BaseFragment> fragmentList;
    private TabLayout mTabLayout;
    private CustomViewPager mViewPager;
    private ArrayList<String> tabTitles;
    private TabLayout.Tab covery;
    private TabLayout.Tab message;
    private TabLayout.Tab user;
    private NewsAdapter newsAdapter;
    private View localView;

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
        mTabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        mViewPager = (CustomViewPager) view.findViewById(R.id.viewpager);
        initTablayout();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initFragment();

    }

    @Override
    protected void bindEvent() {
        initEvents();

    }

    private void initTablayout() {
        localView = LayoutInflater.from(getContext()).inflate(R.layout.tab_discovery, mViewPager, false);
        covery = mTabLayout.newTab();
        covery.setCustomView(localView);
        mTabLayout.addTab(covery);
        localView = LayoutInflater.from(getContext()).inflate(R.layout.tab_message, mViewPager, false);
        message = mTabLayout.newTab();
        message.setCustomView(localView);
        mTabLayout.addTab(message);
        localView = LayoutInflater.from(getContext()).inflate(R.layout.tab_user, mViewPager, false);
        user = mTabLayout.newTab();
        user.setCustomView(localView);
        mTabLayout.addTab(user);
    }

    private void initFragment() {
        tabTitles = new ArrayList();
        tabTitles.add("发现");
        tabTitles.add("消息");
        tabTitles.add("账户");
        fragmentList = new SparseArray<>();
        fragmentList.append(0,new DiscoveryFragment());
        fragmentList.append(1,new FeedFragment());
        fragmentList.append(2,new VideoFragment());
        fragmentList.append(3,new UserFragment());
        newsAdapter = new NewsAdapter(getChildFragmentManager(), fragmentList, tabTitles);
        mViewPager.setAdapter(newsAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setScrollable(true);
        mViewPager.setOffscreenPageLimit(2);
    }

    private void initEvents() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab == mTabLayout.getTabAt(0)) {
                    mViewPager.setCurrentItem(0);
                } else if (tab == mTabLayout.getTabAt(1)) {
                    mViewPager.setCurrentItem(1);
                } else if (tab == mTabLayout.getTabAt(2)) {
                    mViewPager.setCurrentItem(2);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
//                if (tab == mTabLayout.getTabAt(0)) {
//                    covery.setIcon(R.drawable.tab_discovery);
//                } else if (tab == mTabLayout.getTabAt(1)) {
//                    message.setIcon(R.drawable.tab_message);
//                } else if (tab == mTabLayout.getTabAt(2)) {
//                    user.setIcon(R.drawable.tab_user);
//                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position != mTabLayout.getSelectedTabPosition()) {
                    mTabLayout.getTabAt(position).select();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void processClick(View v) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }
}
