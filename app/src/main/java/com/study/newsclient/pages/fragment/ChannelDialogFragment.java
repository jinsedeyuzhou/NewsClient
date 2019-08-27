package com.study.newsclient.pages.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.study.newsclient.R;
import com.study.newsclient.adapter.label.OnEditFinishListener;
import com.study.newsclient.adapter.label.OnItemDragListener;
import com.study.newsclient.adapter.rv.ChannelAdapter;
import com.study.newsclient.adapter.rv.OnEditCompleteListener;
import com.study.newsclient.bean.Channel;
import com.study.newsclient.listener.ItemDragHelperCallBack;
import com.study.newsclient.restful.Constant;
import com.yuxuan.common.adapter.recycler.absrecyclerview.ViewHolder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2018/1/12.
 */

public class ChannelDialogFragment extends DialogFragment implements OnItemDragListener,OnEditCompleteListener {

    private List<Channel> mDatas;
    RecyclerView mRecyclerView;
    private ItemTouchHelper mHelper;
    private ChannelAdapter mAdapter;
    List<Channel> mSelectedDatas;
    List<Channel> mUnSelectedDatas;
    private OnEditCompleteListener mOnEditFinishListener;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEditFinishListener) {
            mOnEditFinishListener = (OnEditCompleteListener) context;
        }
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Dialog dialog = getDialog();
        if (dialog != null) {
            //添加动画
            dialog.getWindow().setWindowAnimations(R.style.dialogSlideAnim);
        }
        return inflater.inflate(R.layout.activity_channel, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        processLogic();
    }

    private void setDataType(List<Channel> datas, int type) {
        for (int i = 0; i < datas.size(); i++) {
            datas.get(i).setItemType(type);
        }
    }

    private void processLogic() {
        Bundle bundle = getArguments();
        List<Channel> selectedDatas = (List<Channel>) bundle.getSerializable(Constant.DATA_SELECTED);
        List<Channel> unselectedDatas = (List<Channel>) bundle.getSerializable(Constant.DATA_UNSELECTED);
        setDataType(selectedDatas, Channel.TYPE_MY_CHANNEL);
        setDataType(unselectedDatas, Channel.TYPE_OTHER_CHANNEL);

        mDatas = new ArrayList<>();
        mDatas.add(new Channel(101,"我的频道",0,0, Channel.TYPE_MY));
        mDatas.add(new Channel(103,"推荐",0,0, Channel.TYPE_NO_MOVE));
        mDatas.addAll(selectedDatas);
        mDatas.add(new Channel( 102,"频道推荐",0,0,Channel.TYPE_OTHER));
        mDatas.addAll(unselectedDatas);


        mAdapter = new ChannelAdapter(getActivity(), mDatas);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 4);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int itemViewType = mDatas.get(position).getItemType();
                return itemViewType == Channel.TYPE_MY_CHANNEL || itemViewType == Channel.TYPE_OTHER_CHANNEL ||itemViewType==Channel.TYPE_NO_MOVE? 1 : 4;
            }
        });
        ItemDragHelperCallBack callBack = new ItemDragHelperCallBack(this);
        mHelper = new ItemTouchHelper(callBack);
        mAdapter.setOnChannelDragListener(this);
        mAdapter.setOnEditCompleteListener(mOnEditFinishListener);
        //attachRecyclerView
        mHelper.attachToRecyclerView(mRecyclerView);
    }


    public static ChannelDialogFragment newInstance() {
        ChannelDialogFragment dialogFragment = new ChannelDialogFragment();
        Bundle bundle = new Bundle();
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    public static ChannelDialogFragment newInstance(List<Channel> selectedDatas, List<Channel> unselectedDatas) {
        ChannelDialogFragment dialogFragment = new ChannelDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.DATA_SELECTED, (Serializable) selectedDatas);
        bundle.putSerializable(Constant.DATA_UNSELECTED, (Serializable) unselectedDatas);
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }


    private DialogInterface.OnDismissListener mOnDismissListener;

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        mOnDismissListener = onDismissListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mOnDismissListener != null)
            mOnDismissListener.onDismiss(dialog);
    }



    @Override
    public void onItemMove(int starPos, int endPos) {
        if (starPos < 0||endPos<0) return;
        //我的频道之间移动
        onMove(starPos, endPos);
    }

    private void onMove(int starPos, int endPos) {
        Channel startChannel = mDatas.get(starPos);
        //先删除之前的位置
        mDatas.remove(starPos);
        //添加到现在的位置
        mDatas.add(endPos, startChannel);
        mAdapter.notifyItemMoved(starPos, endPos);
    }








    @Override
    public void onStarDrag(RecyclerView.ViewHolder viewHolder) {
        mHelper.startDrag(viewHolder);

    }

    @Override
    public void onEditFinish(ArrayList<Channel> selectedLabels, ArrayList<Channel> unselectedLabel, ArrayList<Channel> alwaySelectedLabels) {

    }
}
