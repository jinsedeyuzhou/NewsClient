package com.study.newsclient.adpter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.study.newsclient.base.BaseFragment;
import com.study.newsclient.bean.Channel;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页Fragment 适配,其他有关fragment也可以用
 * 
 * @author wyy
 * 
 */
public class NewsChannelAdapter extends FragmentPagerAdapter {

	private ArrayList<BaseFragment> list;
	private ArrayList<Channel> titles;

	public NewsChannelAdapter(FragmentManager fm, ArrayList<BaseFragment> list, ArrayList<Channel> titles) {
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
		return titles.get(position).getCname();
	}

}
