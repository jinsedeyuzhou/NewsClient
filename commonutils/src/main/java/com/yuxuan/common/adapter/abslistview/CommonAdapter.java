package com.yuxuan.common.adapter.abslistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;


public abstract class CommonAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected ArrayList<T> mDatas;
    protected LayoutInflater mInflater;
    protected int mLayoutId;

    public CommonAdapter(int layoutId, Context mContext, ArrayList<T> mDatas) {
        this.mLayoutId = layoutId;
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return (mDatas == null) ? 0:mDatas.size();
    }

    public void setDatasFeed(ArrayList<T> arrDatasFeed) {
        mDatas = arrDatasFeed;
    }

    public ArrayList<T> getDatasFeed() {
        return mDatas;
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonViewHolder viewHolder = CommonViewHolder.get(mContext, convertView, parent, mLayoutId, position);
        convert(viewHolder,getItem(position),position);
        return viewHolder.getConvertView();
    }

    public abstract void convert(CommonViewHolder holder, T t, int position);
}
