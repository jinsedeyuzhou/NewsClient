package com.study.newsclient.adpter.rv;

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
//        itemView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
//                    mDragStartListener.onStartDrag(holder);
//                }
//                return false;
//            }
//        });
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
        notifyItemRangeChanged(position,mDatas.size());
    }
}
