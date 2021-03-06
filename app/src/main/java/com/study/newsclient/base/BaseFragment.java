package com.study.newsclient.base;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuxuan.common.base.CommonBaseFragment;
import com.yuxuan.common.ebus.BusManager;
import com.yuxuan.common.util.LogUtils;


public abstract class BaseFragment
        extends CommonBaseFragment implements View.OnClickListener {
    private static final String TAG = "BaseFragment";
    protected View mConvertView;
    protected Context mContext;
    protected Resources mResources;
    protected LayoutInflater mInflater;
    private boolean mIsRegisterEvent = false;
    protected boolean mIsFirstVisible = true;

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        mContext = activity;
        mResources = mContext.getResources();
        mInflater = LayoutInflater.from(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View getRootView() {
        return mConvertView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LogUtils.d(TAG, "onCreateView");
        mConvertView = inflater.inflate(getLayoutID(), container, false);
        return mConvertView;
    }

    /**
     * 布局的LayoutID
     * @return
     */
    protected abstract int getLayoutID();


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        bindEvent();
        initData(savedInstanceState);
        boolean isVis = isHidden() || getUserVisibleHint();
        if (isVis && mIsFirstVisible) {
            lazyLoad();
            mIsFirstVisible = false;
        }
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 初始化子View
     * @param view
     * @return
     */
    protected abstract void initView(View view);


    @Override
    public void onStart() {
        if (isRegisterEvent()) {
            BusManager.getBus().register(this);
        }
        super.onStart();
    }


    @Override
    public void onStop() {
        super.onStop();
        if (isRegisterEvent()) {
            BusManager.getBus().unregister(this);
        }
    }

    /**
     * 绑定事件
     */
    protected abstract void bindEvent();
    /**
     * 填充数据
     * @param savedInstanceState
     */
    protected abstract void initData(Bundle savedInstanceState);
    /**
     * 点击事件
     * @param v
     */
    protected abstract void processClick(View v);

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case 0:

                break;

            default:
                break;
        }
        processClick(v);
    }

    /**
     * 数据懒加载
     */
    protected  void lazyLoad(){

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            onVisible();
        } else {
            onInVisible();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            onVisible();
        } else {
            onInVisible();
        }
    }

    /**
     * 当界面可见时的操作
     */
    protected void onVisible() {
        if (mIsFirstVisible && isResumed()) {
            lazyLoad();
            mIsFirstVisible = false;
        }
    }



    /**
     * 当界面不可见时的操作
     */
    protected void onInVisible() {

    }

    protected <E extends View> E F(@IdRes int viewId) {
        if (mConvertView == null) {
            return null;
        }
        return (E) mConvertView.findViewById(viewId);
    }

    protected <E extends View> E F(@NonNull View view, @IdRes int viewId) {
        return (E) view.findViewById(viewId);
    }

    protected <E extends View> void C(@NonNull E view) {
        view.setOnClickListener(this);
    }


    public boolean isRegisterEvent() {
        return mIsRegisterEvent;
    }

    public BaseFragment setRegisterEvent(boolean mIsRegisterEvent) {
        this.mIsRegisterEvent = mIsRegisterEvent;
        return this;
    }

}
