package com.study.newsclient.adpter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * 主页Fragment 适配,其他有关fragment也可以用
 * 
 * @author wyy
 * 
 */
public class NewsFragmentPagerAdapter extends FragmentPagerAdapter {

	private ArrayList<Fragment> list;
	private ArrayList<String> titles;

	public NewsFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> list, ArrayList<String> titles) {
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

}
