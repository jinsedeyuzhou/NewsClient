package com.ebrightmoon.doraemonkit.kit.webdoor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;

import com.ebrightmoon.doraemonkit.R;
import com.ebrightmoon.doraemonkit.constant.BundleKey;
import com.ebrightmoon.doraemonkit.ui.base.BaseFragment;

/**
 * Created by wanglikun on 2019/4/4
 */
public class WebDoorDefaultFragment extends BaseFragment {

    private String mUrl;

    private WebView mWebView;

    @Override
    protected int onRequestLayout() {
        return R.layout.dk_fragment_web_door_default;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUrl = getArguments() == null ? null : getArguments().getString(BundleKey.KEY_URL);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mWebView = findViewById(R.id.web_view);
        mWebView.loadUrl(mUrl);
    }

    @Override
    protected boolean onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        } else {
            return super.onBackPressed();
        }
    }
}