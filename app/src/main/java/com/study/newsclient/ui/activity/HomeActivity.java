package com.study.newsclient.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.study.newsclient.R;
import com.study.newsclient.base.BaseActivity;
import com.study.newsclient.base.NewsActivity;
import com.study.newsclient.ui.fragment.HomeFragment;
import com.study.newsclient.ui.fragment.HomeFragment01;

/**
 * Created by wyy on 2016/9/11.
 *
 */
public class HomeActivity extends NewsActivity {
//    private HomeFragment mHomeFragment;
    private HomeFragment01 mHomeFragment;
    private String curTag="home";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState==null)
        {
//            mHomeFragment = new HomeFragment();
            mHomeFragment = new HomeFragment01();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, mHomeFragment, curTag)
                    .commit();
        }
    }

    @Override
    protected void onResume() {
//        Fragment fragment = getSupportFragmentManager().findFragmentByTag(
//                curTag);
//        if (fragment != null) {
//            fragment.onResume();
//        }
        super.onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Fragment fragment = getSupportFragmentManager().findFragmentByTag(
//                curTag);
//        if (fragment != null) {
//            fragment.onActivityResult(requestCode, resultCode, data);
//        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_home);
    }

    @Override
    public void processClick(View v) {

    }
}
