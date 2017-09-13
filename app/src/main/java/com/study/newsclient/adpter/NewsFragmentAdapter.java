package com.study.newsclient.adpter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.study.newsclient.R;
import com.study.newsclient.base.BaseFragment;
import com.study.newsclient.bean.Channel;

import java.util.ArrayList;
import java.util.List;

public class NewsFragmentAdapter
        extends BaseFramentAdapter {
    private List<Channel> mChannels;

    public NewsFragmentAdapter(Context paramContext, FragmentManager paramFragmentManager, ArrayList<BaseFragment> paramArrayList,ArrayList<Channel> mChannels) {
        super(paramContext,paramFragmentManager,paramArrayList);
        this.mChannels=mChannels;
    }
    public CharSequence getPageTitle(int paramInt) {
        return (this.mChannels.get(paramInt).getCname());
    }


}
