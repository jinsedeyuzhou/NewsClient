package com.study.newsclient.pages.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;

import com.study.newsclient.R;
import com.study.newsclient.adpter.NewsAdapter;
import com.study.newsclient.base.BaseFragment;
import com.study.newsclient.view.BottomNavigationViewHelper;
import com.yuxuan.common.util.StatusBarUtils;

import java.util.ArrayList;

/**
 * Created by wyy on 2017/11/4.
 */

public class HomeFragment02 extends BaseFragment {

    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    private MenuItem menuItem;
    private ArrayList tabTitles;
    private ArrayList fragmentList;
    private NewsAdapter newsAdapter;

    @Override
    protected int getLayoutID() {
        return R.layout.frag_home;
    }

    @Override
    protected void initView(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        bottomNavigationView = (BottomNavigationView)view.findViewById(R.id.bottom_navigation);

        tabTitles = new ArrayList();
        tabTitles.add("发现");
        tabTitles.add("消息");
        tabTitles.add("账户");
        fragmentList = new ArrayList();
        fragmentList.add(new DiscoveryFragment());
        fragmentList.add(new FeedFragment());
        fragmentList.add(new UserFragment());
        fragmentList.add(new UserFragment());
        newsAdapter = new NewsAdapter(getChildFragmentManager(), fragmentList, tabTitles);
        viewPager.setAdapter(newsAdapter);
        viewPager.setCurrentItem(0);
        //默认 >3 的选中效果会影响ViewPager的滑动切换时的效果，故利用反射去掉
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_practice:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.navigation_style:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.navigation_using:
                                viewPager.setCurrentItem(2);
                                break;
                            case R.id.navigation_home:
                                viewPager.setCurrentItem(3);
                                break;
                        }
                        return false;
                    }
                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem = bottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //禁止ViewPager滑动
//        viewPager.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });
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
