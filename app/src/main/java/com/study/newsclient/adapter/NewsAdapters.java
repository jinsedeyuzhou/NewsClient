package com.study.newsclient.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.study.newsclient.R;
import com.study.newsclient.base.BaseFragment;

import java.util.ArrayList;

/**
 * HomeFragment01
 */
public class NewsAdapters
        extends BaseFramentAdapter {
    private int[] imgs;
    private ArrayList<String> titles;

    public NewsAdapters(Context paramContext, FragmentManager paramFragmentManager, ArrayList<BaseFragment> paramArrayList, ArrayList<String> paramArrayList1, int[] paramArrayOfInt) {
        super(paramContext,paramFragmentManager,paramArrayList);
        this.titles = paramArrayList1;
        this.imgs = paramArrayOfInt;
    }


    public CharSequence getPageTitle(int paramInt) {
        return (CharSequence) this.titles.get(paramInt);
    }

    public View getTabView(int paramInt) {
        View localView = LayoutInflater.from(getAdapterContext()).inflate(R.layout.custom_tab, null);
        ((TextView) localView.findViewById(R.id.tv_text)).setText((CharSequence) this.titles.get(paramInt));
        ((ImageView) localView.findViewById(R.id.iv_icon)).setImageResource(this.imgs[paramInt]);
        return localView;
    }
}
