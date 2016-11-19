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

import java.util.ArrayList;

public class NewsAdapters
        extends FragmentPagerAdapter {
    private Context context;
    private int[] imgs;
    private ArrayList<Fragment> list;
    private ArrayList<String> titles;

    public NewsAdapters(Context paramContext, FragmentManager paramFragmentManager, ArrayList<Fragment> paramArrayList, ArrayList<String> paramArrayList1, int[] paramArrayOfInt) {
        super(paramFragmentManager);
        this.list = paramArrayList;
        this.titles = paramArrayList1;
        this.imgs = paramArrayOfInt;
        this.context = paramContext;
    }

    public int getCount() {
        return this.list.size();
    }

    public Fragment getItem(int paramInt) {
        return (Fragment) this.list.get(paramInt);
    }

    public CharSequence getPageTitle(int paramInt) {
        return (CharSequence) this.titles.get(paramInt);
    }

    public View getTabView(int paramInt) {
        View localView = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
        ((TextView) localView.findViewById(R.id.tv_text)).setText((CharSequence) this.titles.get(paramInt));
        ((ImageView) localView.findViewById(R.id.iv_icon)).setImageResource(this.imgs[paramInt]);
        return localView;
    }
}
