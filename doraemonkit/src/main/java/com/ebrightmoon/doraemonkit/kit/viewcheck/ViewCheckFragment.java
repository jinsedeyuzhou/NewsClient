package com.ebrightmoon.doraemonkit.kit.viewcheck;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ebrightmoon.doraemonkit.R;
import com.ebrightmoon.doraemonkit.config.ViewCheckConfig;
import com.ebrightmoon.doraemonkit.constant.PageTag;
import com.ebrightmoon.doraemonkit.ui.base.BaseFragment;
import com.ebrightmoon.doraemonkit.ui.base.FloatPageManager;
import com.ebrightmoon.doraemonkit.ui.base.PageIntent;
import com.ebrightmoon.doraemonkit.ui.setting.SettingItem;
import com.ebrightmoon.doraemonkit.ui.setting.SettingItemAdapter;
import com.ebrightmoon.doraemonkit.ui.widget.titlebar.HomeTitleBar;

/**
 * Created by wanglikun on 2018/11/23.
 */

public class ViewCheckFragment extends BaseFragment {
    private HomeTitleBar mTitleBar;
    private RecyclerView mSettingList;
    private SettingItemAdapter mSettingItemAdapter;

    @Override
    protected int onRequestLayout() {
        return R.layout.dk_fragment_view_check;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        mTitleBar = findViewById(R.id.title_bar);
        mTitleBar.setListener(new HomeTitleBar.OnTitleBarClickListener() {
            @Override
            public void onRightClick() {
                getActivity().finish();
            }
        });
        mSettingList = findViewById(R.id.setting_list);
        mSettingList.setLayoutManager(new LinearLayoutManager(getContext()));
        mSettingItemAdapter = new SettingItemAdapter(getContext());
        mSettingItemAdapter.setOnSettingItemSwitchListener(new SettingItemAdapter.OnSettingItemSwitchListener() {
            @Override
            public void onSettingItemSwitch(View view, SettingItem data, boolean on) {
                if (data.desc == R.string.dk_kit_view_check) {
                    if (on) {
                        PageIntent intent = new PageIntent(ViewCheckFloatPage.class);
                        intent.tag = PageTag.PAGE_VIEW_CHECK;
                        FloatPageManager.getInstance().add(intent);
                        FloatPageManager.getInstance().add(new PageIntent(ViewCheckInfoFloatPage.class));
                        FloatPageManager.getInstance().add(new PageIntent(ViewCheckDrawFloatPage.class));
                        getActivity().finish();
                    } else {
                        FloatPageManager.getInstance().removeAll(ViewCheckDrawFloatPage.class);
                        FloatPageManager.getInstance().removeAll(ViewCheckInfoFloatPage.class);
                        FloatPageManager.getInstance().removeAll(ViewCheckFloatPage.class);
                    }
                    ViewCheckConfig.setViewCheckOpen(getContext(), on);
                }
            }
        });
        mSettingItemAdapter.append(new SettingItem(R.string.dk_kit_view_check, ViewCheckConfig.isViewCheckOpen(getContext())));
        mSettingList.setAdapter(mSettingItemAdapter);
    }
}