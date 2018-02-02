package com.study.newsclient.pages.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.study.newsclient.R;
import com.study.newsclient.adapter.rv.ChatAdapterForRv;
import com.study.newsclient.base.BaseActivity;
import com.study.newsclient.bean.ChatMessage;
import com.yuxuan.common.adapter.recycler.absrecyclerview.LoadMoreWrapper;
import com.yuxuan.common.util.T;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/10.
 */

public class RobotActivity extends BaseActivity {
    private List<ChatMessage> mDatas = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private ChatAdapterForRv adapter;
    private EditText mEtMsgInput;
    private Button mBtnMsgSend;
    private LoadMoreWrapper mLoadMoreWrapper;

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_robot);
    }

    @Override
    public void initData() {

    }

    /**
     * 初始化标题
     */
    private void initTitleBar() {
        RelativeLayout bar_rl_left = (RelativeLayout) findViewById(R.id.bar_rl_left);
        bar_rl_left.setVisibility(View.GONE);
        bar_rl_left.setOnClickListener(this);
        TextView bar_tv_title = (TextView) findViewById(R.id.bar_tv_title);
        bar_tv_title.setText("Robot");

    }

    @Override
    public void initView() {
        initTitleBar();

        mEtMsgInput = (EditText) findViewById(R.id.et_msg_input);
        mBtnMsgSend = (Button) findViewById(R.id.btn_msg_send);

        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDatas.addAll(ChatMessage.MOCK_DATAS);
        adapter = new ChatAdapterForRv(mContext, mDatas);
//        mLoadMoreWrapper = new LoadMoreWrapper(adapter);
//        mLoadMoreWrapper.setLoadMoreView(LayoutInflater.from(mContext).inflate(R.layout.default_loading, mRecyclerView, false));
//        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
//            @Override
//            public void onLoadMoreRequested() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        boolean coming = Math.random() > 0.5;
//                        ChatMessage msg = null;
//                        msg = new ChatMessage(coming ? R.drawable.ic_meinv : R.drawable.ic_gu, coming ? "meinv" : "guzhuang", "where are you " + mDatas.size(),
//                                null, coming);
//                        mDatas.add(msg);
//                        mLoadMoreWrapper.notifyDataSetChanged();
//
//                    }
//                }, 3000);
//            }
//        });

        mRecyclerView.setAdapter(adapter);

    }

    @Override
    protected void bindEvent() {
        mBtnMsgSend.setOnClickListener(this);
    }

    @Override
    public void processClick(View paramView) {
        switch (paramView.getId()) {
            case R.id.btn_msg_send:
                sendMsg();
                break;
            default:
                break;
        }

    }


    private void sendMsg() {
        final String msgStr = mEtMsgInput.getText().toString();
        if (TextUtils.isEmpty(msgStr))
        {
            T.showShort(mContext,"没有输入内容呢");
            return ;

        }

        boolean coming = Math.random() > 0.5;
        ChatMessage msg = null;
        msg = new ChatMessage(coming ? R.drawable.ic_meinv : R.drawable.ic_gu, coming ? "meinv" : "guzhuang", "where are you " + mDatas.size(),
                null, coming);
        mDatas.add(msg);
        adapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(mDatas.size() - 1);

        mEtMsgInput.setText("");

        // 关闭软键盘
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        // 得到InputMethodManager的实例
        if (imm.isActive())
        {
            // 如果开启
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
                    InputMethodManager.HIDE_NOT_ALWAYS);
            // 关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
        }


        //请求网络更新数据

    }
}
