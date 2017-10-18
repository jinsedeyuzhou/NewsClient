package com.study.newsclient.adpter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.study.newsclient.base.BaseFragment;
import com.study.newsclient.bean.Channel;
import com.study.newsclient.pages.fragment.NewsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页Fragment 适配,其他有关fragment也可以用
 *
 * @author wyy
 */
public class NewsChannelAdapter
        extends BaseFramentAdapter {
    private ArrayList<Channel> mChannels;

    public NewsChannelAdapter(Context paramContext, FragmentManager paramFragmentManager, ArrayList<BaseFragment> paramArrayList, ArrayList<Channel> mChannels) {
        super(paramContext, paramFragmentManager, paramArrayList);
        this.mChannels = mChannels;
    }

    public CharSequence getPageTitle(int position) {
        return (this.mChannels.get(position).getCname());
    }

}
