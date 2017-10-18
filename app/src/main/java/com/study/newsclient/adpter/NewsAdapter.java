package com.study.newsclient.adpter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * 主页HomeFragment 适配,其他有关fragment也可以用
 * 
 * @author wyy
 * 
 */
public class NewsAdapter extends FragmentPagerAdapter {

	private ArrayList<Fragment> list;
	private ArrayList<String> titles;

	public NewsAdapter(FragmentManager fm, ArrayList<Fragment> list, ArrayList<String> titles) {
		super(fm);
		this.list = list;
		this.titles=titles;
	}


	@Override
	public Fragment getItem(int position) {
		return list.get(position);
	}

	@Override
	public int getCount() {
		return list.size();
	}


	@Override
	public CharSequence getPageTitle(int position) {
		return titles.get(position);
	}

}
