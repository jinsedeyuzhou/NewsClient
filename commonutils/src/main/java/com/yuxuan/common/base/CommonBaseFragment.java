package com.yuxuan.common.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuxuan.common.util.LogUtils;


/**
 * Created by wyy on 2016/9/11.
 * 1、如果你在用24.0.0+的版本，需要特殊处理，官方已经修复Fragment重叠问题
 * 2、startActivityForResult接收返回问题
 * 在support 23.2.0以下的支持库中，对于在嵌套子Fragment的startActivityForResult ()，
 * 会发现无论如何都不能在onActivityResult()中接收到返回值，
 * 只有最顶层的父Fragment才能接收到，这是一个support v4库的一个BUG，不过在前两天发布的support 23.2.0库中，
 * 已经修复了该问题，嵌套的子Fragment也能正常接收到返回数据了!
 */
public abstract class CommonBaseFragment extends Fragment {
    private static final String TAG = "CommonBaseFragment";
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtils.d(TAG, "onActivityCreated");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.d(TAG, "oncreate");
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtils.d(TAG, "onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onAttach(Context activity) {
        LogUtils.d(TAG, "onAttach");
        super.onAttach(activity);
    }

    @Override
    public void onStart() {
        LogUtils.d(TAG, "onStart");
        super.onStart();
    }

    @Override
    public void onStop() {
        LogUtils.d(TAG, "onStop");
        super.onStop();

    }

    @Override
    public void onResume() {
        LogUtils.d(TAG, "onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        LogUtils.d(TAG, "onPause");
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        LogUtils.d(TAG, "onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        LogUtils.d(TAG, "onDetach");
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        LogUtils.d(TAG, "onDestroy");
        super.onDestroy();
    }


}
