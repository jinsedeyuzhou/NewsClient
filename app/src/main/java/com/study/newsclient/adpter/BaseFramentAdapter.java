package com.study.newsclient.adpter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.study.newsclient.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyy on 2017/9/10.
 */

public abstract class BaseFramentAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> list;
    private Context paramContext;


    public BaseFramentAdapter(Context paramContext, FragmentManager fm, ArrayList<BaseFragment> list) {
        super(fm);
        this.paramContext = paramContext;
        this.list = list;
    }


    @Override
    public BaseFragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public Context getAdapterContext() {
        if (paramContext != null)
            return paramContext;
        return null;
    }

}
