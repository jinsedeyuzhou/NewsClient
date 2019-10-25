package com.ebrightmoon.doraemonkit.kit.network.ui;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.ebrightmoon.doraemonkit.kit.network.bean.NetworkRecord;

import java.util.List;

public class NetworkPagerAdapter extends PagerAdapter {
    private List<NetworkDetailView> views;
    private NetworkRecord mRecord;

    public NetworkPagerAdapter(List<NetworkDetailView> views, NetworkRecord record) {
        this.views = views;
        mRecord = record;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        NetworkDetailView view = views.get(position);
        if (position == 0) {
            view.bindRequest(mRecord);
        } else {
            view.bindResponse(mRecord);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }
}