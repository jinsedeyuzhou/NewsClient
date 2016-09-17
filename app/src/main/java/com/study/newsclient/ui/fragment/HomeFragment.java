package com.study.newsclient.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.study.newsclient.R;
import com.study.newsclient.adpter.NewsAdapter;
import com.study.newsclient.base.BaseFragment;
import com.study.newsclient.view.CustomViewPager;

import java.util.ArrayList;

/**
 * Created by wyy on 2016/9/17.
 */
public class HomeFragment extends BaseFragment {
    private ArrayList<Fragment> fragmentList;
    private TabLayout mTabLayout;
    private CustomViewPager mViewPager;
    private ArrayList<String> tabTitles;
    private TabLayout.Tab one;
    private TabLayout.Tab two;
    private TabLayout.Tab three;

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container) {
        View view=inflater.inflate(R.layout.fragment_home,container,false);
        mTabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        mViewPager = (CustomViewPager) view.findViewById(R.id.viewpager);

        return view;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        tabTitles=new ArrayList<>();
        tabTitles.add("发现");
        tabTitles.add("消息");
        tabTitles.add("账户");

        fragmentList=new ArrayList<>();
        DiscoveryFragment discovery=new DiscoveryFragment();
        fragmentList.add(discovery);
        FeedFragment feed=new FeedFragment();
        fragmentList.add(feed);
        AccountFragment account=new AccountFragment();
        fragmentList.add(account);
        NewsAdapter newsAdapter=new NewsAdapter(getChildFragmentManager(),fragmentList,tabTitles);
        mViewPager.setAdapter(newsAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setScrollable(true);
        mTabLayout.setupWithViewPager(mViewPager);

        one = mTabLayout.getTabAt(0);
        two = mTabLayout.getTabAt(1);
        three = mTabLayout.getTabAt(2);

        one.setIcon(R.drawable.tab_discovery);
        two.setIcon(R.drawable.tab_message);
        three.setIcon(R.drawable.tab_account);


        initEvents();




    }

    private void initEvents() {


        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab == mTabLayout.getTabAt(0)) {
                    one.setIcon(R.drawable.tab_discovery);
                    mViewPager.setCurrentItem(0);
                } else if (tab == mTabLayout.getTabAt(1)) {
                    two.setIcon(R.drawable.tab_message);
                    mViewPager.setCurrentItem(1);
                } else if (tab == mTabLayout.getTabAt(2)) {
                    three.setIcon(R.drawable.tab_account);
                    mViewPager.setCurrentItem(2);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab == mTabLayout.getTabAt(0)) {
                    one.setIcon(R.drawable.tab_discovery);
                } else if (tab == mTabLayout.getTabAt(1)) {
                    two.setIcon(R.drawable.tab_message);
                } else if (tab == mTabLayout.getTabAt(2)) {
                    three.setIcon(R.drawable.tab_account);
                }
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

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void processClick(View v) {

    }
}
