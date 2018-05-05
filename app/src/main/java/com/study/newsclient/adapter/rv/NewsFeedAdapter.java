package com.study.newsclient.adapter.rv;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;


import com.study.newsclient.R;
import com.yuxuan.common.adapter.recycler.absrecyclerview.CommonAdapter;
import com.yuxuan.common.adapter.recycler.absrecyclerview.ViewHolder;
import com.yuxuan.common.adapter.recycler.helper.ItemTouchHelperAdapter;
import com.yuxuan.common.adapter.recycler.helper.OnStartDragListener;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2017/12/7.
 * 可以滑动删除 如果需要添加下拉加载功能需要，需要重新调整架构，只有最后一个adapter才会生效
 * ItemTouchHelperViewHolder 可以设置ViewHolder的选中背景
 */

public class NewsFeedAdapter extends CommonAdapter implements ItemTouchHelperAdapter {
    private final OnStartDragListener mDragStartListener;
    private List<String> mDatas;
    private View rootView;

    public NewsFeedAdapter(Context context, int layoutId, List datas,OnStartDragListener dragStartListener) {
        super(context, layoutId, datas);
        this.mDragStartListener=dragStartListener;
        this.mDatas=datas;
    }

    @Override
    protected void convert(ViewHolder holder, Object o, int position) {
        holder.setText(R.id.nickname, mDatas.get(position));
    }

    @Override
    public void onViewHolderCreated(final ViewHolder holder, View itemView) {
        super.onViewHolderCreated(holder, itemView);
        //此事件会屏蔽删除 itemView相当于itemview中的一个子View
        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mDatas, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }
}
