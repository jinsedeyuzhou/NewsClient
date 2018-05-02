package com.study.newsclient.pages.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ebrightmoon.retrofitrx.callback.ACallback;
import com.ebrightmoon.retrofitrx.common.GsonUtil;
import com.ebrightmoon.retrofitrx.retrofit.AppClient;
import com.study.newsclient.R;
import com.study.newsclient.adapter.rv.ChatAdapterForRv;
import com.study.newsclient.base.BaseActivity;
import com.study.newsclient.bean.ChatMessage;
import com.study.newsclient.entity.PerceptionBean;
import com.study.newsclient.entity.RequestRobotParams;
import com.study.newsclient.entity.ResponseRobotBean;
import com.study.newsclient.entity.UserInfoBean;
import com.study.newsclient.utils.T;
import com.yuxuan.common.adapter.recycler.absrecyclerview.LoadMoreWrapper;
import com.yuxuan.common.util.CustomToast;

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
//    private LinearLayout mRobotContent;

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
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

//        mRobotContent = (LinearLayout) findViewById(R.id.ll_robot_content);
        mEtMsgInput = (EditText) findViewById(R.id.et_msg_input);
        mBtnMsgSend = (Button) findViewById(R.id.btn_msg_send);

        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        mDatas.addAll(ChatMessage.MOCK_DATAS);
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
//        mRobotContent.setOnClickListener(this);
    }

    @Override
    public void processClick(View paramView) {
        switch (paramView.getId()) {
            case R.id.btn_msg_send:
                sendMsg();
                break;
//            case R.id.ll_robot_content:
//                // 关闭软键盘
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                // 得到InputMethodManager的实例
//                if (imm.isActive()) {
//                    // 如果开启
//                    imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
//                            InputMethodManager.HIDE_NOT_ALWAYS);
//                    // 关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
//                }
//                break;
            default:

        }

    }


    private void sendMsg() {
        final String msgStr = mEtMsgInput.getText().toString();
        if (TextUtils.isEmpty(msgStr)) {
            T.showShort(mContext, "没有输入内容呢");
            return;

        }

        boolean coming = Math.random() > 0.5;
        ChatMessage msg = null;
//        msg = new ChatMessage(coming ? R.drawable.ic_meinv : R.drawable.ic_gu, coming ? "meinv" : "guzhuang", msgStr,
//                null, coming);
        msg = new ChatMessage(R.drawable.ic_gu, "西施", msgStr,
                null, false);
        mDatas.add(msg);
        adapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(mDatas.size() - 1);


        getResponseData(msgStr);
        mEtMsgInput.setText("");

        // 关闭软键盘
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        // 得到InputMethodManager的实例
        if (imm.isActive()) {
            // 如果开启
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
                    InputMethodManager.HIDE_NOT_ALWAYS);
            // 关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
        }


        //请求网络更新数据

    }

    /**
     * 获取回答数据
     */
    private void getResponseData(String msg) {
        RequestRobotParams requestRobotParams = new RequestRobotParams();
        requestRobotParams.setReqType(0);

        UserInfoBean userInfoBean = new UserInfoBean();
        userInfoBean.setApiKey("4ae693ed64c74ae0ae70c866bcd39448");
        userInfoBean.setUserId("2");
        requestRobotParams.setUserInfo(userInfoBean);

        PerceptionBean perceptionBean = new PerceptionBean();
        PerceptionBean.InputTextBean inputTextBean = new PerceptionBean.InputTextBean();
        inputTextBean.setText(msg);
        perceptionBean.setInputText(inputTextBean);


        PerceptionBean.SelfInfoBean selfInfoBean = new PerceptionBean.SelfInfoBean();
        PerceptionBean.SelfInfoBean.LocationBean locationBean = new PerceptionBean.SelfInfoBean.LocationBean();
        locationBean.setCity("北京");
        locationBean.setProvince("北京");
        locationBean.setStreet("信息路");
        selfInfoBean.setLocation(locationBean);
        perceptionBean.setSelfInfo(selfInfoBean);
        requestRobotParams.setPerception(perceptionBean);

        AppClient.getInstance().json("openapi/api/v2", GsonUtil.gson().toJson(requestRobotParams), new ACallback<ResponseRobotBean>() {
            @Override
            public void onSuccess(ResponseRobotBean data) {
                initViewData(data);
            }

            @Override
            public void onFail(int errCode, String errMsg) {
                T.showShort(mContext, errMsg);
            }
        });


    }

    /**
     * 初始化数据
     */
    private void initViewData(ResponseRobotBean data) {
        ChatMessage msg = new ChatMessage();
        List<ResponseRobotBean.ResultsBean> results = data.getResults();
        StringBuilder content = new StringBuilder();

        for (int i = 0; i < results.size(); i++) {
            if (results.get(i).getResultType().equals("url")) {
                content.append(results.get(i).getValues().getUrl());
            } else if (results.get(i).getResultType().equals("text")) {
                content.append(results.get(i).getValues().getText());
            }else if (results.get(i).getResultType().equals("image"))
            {
                msg.setType(2);
                content.append(results.get(i).getValues().getImage());
            }
        }
        if (mDatas.size()>3)
        {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL, false);
            linearLayoutManager.setStackFromEnd(true);
            mRecyclerView.setLayoutManager(linearLayoutManager);
        }
        msg.setIcon(R.drawable.ic_meinv);
        msg.setName("王昭君");
        msg.setContent(content.toString());
        msg.setComMeg(true);
        mDatas.add(msg);
        adapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(mDatas.size() - 1);

    }
}
