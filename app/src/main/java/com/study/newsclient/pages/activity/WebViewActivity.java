package com.study.newsclient.pages.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.LinearLayout;

import com.study.newsclient.R;
import com.study.newsclient.base.BaseSwipeBackActivity;
import com.study.newsclient.view.ProgressWebView;


/**
 * Created by wyy on 2017/10/19.
 * 解决webview内容泄露问题
 */

public class WebViewActivity extends BaseSwipeBackActivity {

    private ProgressWebView mWebView;
    private LinearLayout mWebViewContent;

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_webview);
    }

    @Override
    public void initData() {
        mWebView.loadUrl("http://www.aswifter.com/");
    }

    @Override
    public void initView() {
        mWebViewContent = (LinearLayout) findViewById(R.id.ll_webview_content);
        mWebView = new ProgressWebView(getApplicationContext());
        mWebViewContent.addView(mWebView);
        initWebViewSetting(mWebView.getSettings());


    }

    /**
     * 初始化webview设置
     */
    private void initWebViewSetting(WebSettings mWebViewSetting) {
        mWebViewSetting.setJavaScriptEnabled(true);
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
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();

            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
