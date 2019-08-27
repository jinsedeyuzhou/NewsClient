package com.study.newsclient.listener;


import android.support.v7.widget.RecyclerView;

import com.yuxuan.common.adapter.recycler.absrecyclerview.ViewHolder;

/**
 * Created by Administrator on 2017/1/5 0005.
 */

public interface OnChannelDragListener extends OnChannelListener {
    void onStarDrag(RecyclerView.ViewHolder baseViewHolder);

}
