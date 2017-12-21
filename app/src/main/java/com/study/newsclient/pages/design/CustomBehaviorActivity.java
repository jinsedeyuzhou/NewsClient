package com.study.newsclient.pages.design;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.study.newsclient.R;
import com.study.newsclient.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/19.
 */

public class CustomBehaviorActivity extends BaseActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_behavior);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        MyAdapter adapter = new MyAdapter();
        mRecyclerView.setAdapter(adapter);
        adapter.setData(mockData());
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void bindEvent() {

    }

    @Override
    public void processClick(View paramView) {

    }



    private List<String> mockData(){
        List<String> data = new ArrayList<>();
        for (int i=0;i<100;i++){
            data.add("item:"+i);
        }
        return data;
    }


    public static class MyAdapter extends RecyclerView.Adapter{
        private List<String> mData;

        public void setData(List<String> data) {
            mData = data;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_behavior,null));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//            ((MyViewHolder)holder).mTextView.setText(mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData == null ? 0:mData.size();
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
//        private TextView mTextView;
        public MyViewHolder(View itemView) {
            super(itemView);
//            mTextView = (TextView) itemView.findViewById(R.id.item_content_text);
        }
    }
}
