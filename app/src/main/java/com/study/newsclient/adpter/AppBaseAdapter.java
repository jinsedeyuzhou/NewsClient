package com.study.newsclient.adpter;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Adapter基类
 * 
 * @author
 * @version 1.0
 * @param <T>
 * @param <Q>
 */
public abstract class AppBaseAdapter<T, Q> extends BaseAdapter {

	public Context context;
	public List<T> list;//
	public Q view; // 这里不一定是ListView,比如GridView,CustomListView


	public AppBaseAdapter(Context context, List<T> list, Q view) {
		this.context = context;
		this.list = list;
		this.view = view;
	}

	public AppBaseAdapter(Context context, List<T> list) {
		this.context = context;
		this.list = list;
		
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}


}
