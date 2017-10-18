package com.study.newsclient.pages.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.study.newsclient.R;
import com.study.newsclient.base.BaseFragment;
import com.yuxuan.common.adapter.absrecyclerview.CommonAdapter;
import com.yuxuan.common.adapter.absrecyclerview.EmptyWrapper;
import com.yuxuan.common.adapter.absrecyclerview.HeaderAndFooterWrapper;
import com.yuxuan.common.adapter.absrecyclerview.LoadMoreWrapper;
import com.yuxuan.common.adapter.absrecyclerview.MultiItemTypeAdapter;
import com.yuxuan.common.adapter.absrecyclerview.ViewHolder;
import com.yuxuan.common.adapter.recycler.DividerItemDecoration;

import java.util.ArrayList;

/**
 * Created by wyy on 2017/9/4.
 */

public class NewsFragment extends BaseFragment {


    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerViewNews;
    private CommonAdapter<String> mAdapter;
    private ArrayList<String> datas;
    private EmptyWrapper mEmptyWrapper;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private LoadMoreWrapper mLoadMoreWrapper;
    private Handler handler = new Handler();
    private LinearLayoutManager linearLayoutManager;

    public static NewsFragment newInstance(int channelId) {
        NewsFragment newsFragment = new NewsFragment();

        Bundle bundle = new Bundle();
        bundle.putInt("key_channel_id",channelId);
        newsFragment.setArguments(bundle);

        return newsFragment;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initView(View view) {
        datas=new ArrayList<>();
//        initDatas();

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_news);
        mRecyclerViewNews = (RecyclerView) view.findViewById(R.id.rv_news);
        mSwipeRefreshLayout.setColorSchemeColors(mResources.getColor(R.color.colorAccent));

        linearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerViewNews.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerViewNews.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL_LIST));

    }

    @Override
    protected void bindEvent() {
       mRecyclerViewNews.addOnScrollListener(new RecyclerView.OnScrollListener() {
           @Override
           public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
               super.onScrollStateChanged(recyclerView, newState);
           }

           @Override
           public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
               super.onScrolled(recyclerView, dx, dy);
               int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
               if (lastVisibleItemPosition + 1 == mAdapter.getItemCount()) {
                    mSwipeRefreshLayout.setRefreshing(false);
               }

           }
       });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });

        mAdapter=new CommonAdapter<String>(mContext,R.layout.item_news,datas) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.id_item_list_title, s + " : " + holder.getAdapterPosition() + " , " + holder.getLayoutPosition());
            }
        };

        initEmptyView();
        initHeaderAndFooter();
        mLoadMoreWrapper = new LoadMoreWrapper(mHeaderAndFooterWrapper);
        mLoadMoreWrapper.setLoadMoreView(R.layout.default_loading);
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
                        for (int i = 0; i < 10; i++)
                        {
                            datas.add("Add:" + i);
                        }
                        mLoadMoreWrapper.notifyDataSetChanged();

                    }
                }, 3000);
            }
        });

        mRecyclerViewNews.setAdapter(mLoadMoreWrapper);
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });



    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        int key_channel_id = getArguments().getInt("key_channel_id");
    }

    @Override
    protected void processClick(View v) {

    }


    private void initEmptyView()
    {
        mEmptyWrapper = new EmptyWrapper(mAdapter);
        mEmptyWrapper.setEmptyView(LayoutInflater.from(mContext).inflate(R.layout.item_empty, mRecyclerViewNews, false));
        mRecyclerViewNews.setAdapter(mEmptyWrapper);
    }

    private void initHeaderAndFooter()
    {
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);

        TextView t1 = new TextView(mContext);
        t1.setText("Header 1");
        mHeaderAndFooterWrapper.addHeaderView(t1);
    }

    private void initDatas()
    {
        for (int i = 'A'; i <= 'z'; i++)
        {
            datas.add((char) i + "");
        }
    }
}
