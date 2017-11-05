package com.study.newsclient.pages.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.study.newsclient.R;
import com.study.newsclient.base.BaseSwipeBackActivity;
import com.study.newsclient.view.ProgressWebView;


/**
 * Created by wyy on 2017/10/19.
 */

public class WebViewActivity extends BaseSwipeBackActivity {

    private ProgressWebView mWebView;

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_webview);
        setEnableSwipe(true);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        mWebView = (ProgressWebView) findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("http://www.aswifter.com/");

    }

    @Override
    protected boolean isNeedAnimation() {
        return false;
    }

    @Override
    protected void bindEvent() {

    }

    @Override
    public void processClick(View paramView) {

    }

    @Override
    public boolean isRegisterEvent() {
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(mWebView.canGoBack()){
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
