package com.study.newsclient.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.study.newsclient.base.BaseFragment;

import java.util.ArrayList;

/**
 * FragmentStatePagerAdapter 在会在因 POSITION_NONE 触发调用的 destroyItem() 中真正的释放资源，重新建立一个新的 Fragment；
 * 而 FragmentPagerAdapter 仅仅会在 destroyItem() 中 detach 这个 Fragment，在 instantiateItem() 时会使用旧的 Fragment，并触发 attach，因此没有释放资源及重建的过程。
 * Created by wyy on 2017/9/10.
 */

public abstract class BaseFramentAdapter extends FragmentPagerAdapter {
    private ArrayList<BaseFragment> list;
    private Context paramContext;


    public BaseFramentAdapter(Context paramContext, FragmentManager fm, ArrayList<BaseFragment> list) {
        super(fm);
        this.paramContext = paramContext;
        this.list = list;
    }

    /**
     * 返回POSITION_NONE，
     * @param object
     * @return
     */
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
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
