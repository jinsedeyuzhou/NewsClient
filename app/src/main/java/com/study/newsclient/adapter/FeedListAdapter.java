package com.study.newsclient.adapter;

import android.content.Context;

import com.yuxuan.common.adapter.recycler.absrecyclerview.MultiItemTypeAdapter;

import java.util.List;

/**
 * Created by wyy on 2018/2/22.
 */

public class FeedListAdapter<T> extends MultiItemTypeAdapter<T> {
    public FeedListAdapter(Context context, List<T> datas) {
        super(context, datas);
    }
}
