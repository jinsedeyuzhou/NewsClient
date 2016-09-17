package com.study.newsclient.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.study.newsclient.utils.LogUtils;

/**
 * Created by wyy on 2016/9/11.
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "BaseFragment";
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";
    protected Context ct;
    private NewsApplication app;
    protected View rootView;

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtils.d(TAG, "onActivityCreated");
        app = (NewsApplication) getActivity().getApplication();
        initData(savedInstanceState);
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
        ct = getActivity();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    public View getRootView() {
        return rootView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LogUtils.d(TAG, "onCreateView");
        rootView = initViews(inflater, container);
        return rootView;
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

    protected abstract View initViews(LayoutInflater inflater,
                                     ViewGroup container);

    protected abstract void initData(Bundle savedInstanceState);

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

}
