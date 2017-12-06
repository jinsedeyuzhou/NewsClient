package com.study.newsclient.pages.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.study.newsclient.R;
import com.study.newsclient.adpter.rv.ChatAdapterForRv;
import com.study.newsclient.base.BaseFragment;
import com.study.newsclient.bean.ChatMessage;
import com.yuxuan.common.adapter.absrecyclerview.CommonAdapter;
import com.yuxuan.common.adapter.absrecyclerview.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyy on 2016/9/12.
 */
public class FeedFragment extends BaseFragment {
    private List<ChatMessage> mDatas = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private LoadMoreWrapper mLoadMoreWrapper;
    private ChatAdapterForRv adapter;

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_feed;
    }

    @Override
    protected void initView(View view) {
        mRecyclerView = (RecyclerView)view. findViewById(R.id.id_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDatas.addAll(ChatMessage.MOCK_DATAS);
        adapter = new ChatAdapterForRv(mContext, mDatas);

        mLoadMoreWrapper = new LoadMoreWrapper(adapter);
        mLoadMoreWrapper.setLoadMoreView(LayoutInflater.from(mContext).inflate(R.layout.default_loading, mRecyclerView, false));
        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener()
        {
            @Override
            public void onLoadMoreRequested()
            {
                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        boolean coming = Math.random() > 0.5;
                        ChatMessage msg = null;
                        msg = new ChatMessage(coming ? R.drawable.ic_meinv : R.drawable.ic_gu, coming ? "meinv" : "guzhuang", "where are you " + mDatas.size(),
                                null, coming);
                        mDatas.add(msg);
                        mLoadMoreWrapper.notifyDataSetChanged();

                    }
                }, 3000);
            }
        });

        adapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder,  int position)
            {
                Toast.makeText(mContext, "Click:" + position , Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position)
            {
                Toast.makeText(mContext, "LongClick:" + position , Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        mRecyclerView.setAdapter(mLoadMoreWrapper);


    }

    @Override
    protected void bindEvent() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void processClick(View v) {

    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
