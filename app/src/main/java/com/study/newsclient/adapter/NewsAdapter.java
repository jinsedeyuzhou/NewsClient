package com.study.newsclient.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;

import com.study.newsclient.base.BaseFragment;

import java.util.ArrayList;

/**
 * 主页HomeFragment 适配,其他有关fragment也可以用
 * 
 * @author wyy
 * 
 */
public class NewsAdapter extends FragmentPagerAdapter {

	private SparseArray<BaseFragment> list;
	private ArrayList<String> titles;

	public NewsAdapter(FragmentManager fm, SparseArray<BaseFragment> list, ArrayList<String> titles) {
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
