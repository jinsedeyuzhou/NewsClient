package com.ebrightmoon.doraemonkit.kit.viewcheck;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.ebrightmoon.doraemonkit.R;
import com.ebrightmoon.doraemonkit.constant.PageTag;
import com.ebrightmoon.doraemonkit.ui.base.BaseFloatPage;
import com.ebrightmoon.doraemonkit.ui.base.FloatPageManager;
import com.ebrightmoon.doraemonkit.ui.viewcheck.LayoutBorderView;
import com.ebrightmoon.doraemonkit.util.UIUtils;

/**
 * Created by wanglikun on 2018/12/3.
 */

public class ViewCheckDrawFloatPage extends BaseFloatPage implements ViewCheckFloatPage.OnViewSelectListener {
    private LayoutBorderView mLayoutBorderView;

    @Override
    protected void onCreate(Context context) {
        super.onCreate(context);
        ViewCheckFloatPage page = (ViewCheckFloatPage) FloatPageManager.getInstance().getFloatPage(PageTag.PAGE_VIEW_CHECK);
        page.setViewSelectListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViewCheckFloatPage page = (ViewCheckFloatPage) FloatPageManager.getInstance().getFloatPage(PageTag.PAGE_VIEW_CHECK);
        if (page != null) {
            page.removeViewSelectListener(this);
        }
    }

    @Override
    protected View onCreateView(Context context, ViewGroup view) {
        return LayoutInflater.from(context).inflate(R.layout.dk_float_view_check_draw, null);
    }

    @Override
    protected void onLayoutParamsCreated(WindowManager.LayoutParams params) {
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
    }

    @Override
    protected void onViewCreated(View view) {
        super.onViewCreated(view);
        mLayoutBorderView = findViewById(R.id.rect_view);
    }


    @Override
    public void onViewSelected(View view) {
        if (view == null) {
            mLayoutBorderView.showViewLayoutBorder(null);
        } else {
            mLayoutBorderView.showViewLayoutBorder(UIUtils.getViewRect(view));
        }
    }

    @Override
    public void onEnterForeground() {
        super.onEnterForeground();
        getRootView().setVisibility(View.VISIBLE);
    }

    @Override
    public void onEnterBackground() {
        super.onEnterBackground();
        getRootView().setVisibility(View.GONE);
    }
}
