package com.ebrightmoon.doraemonkit.kit.alignruler;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ebrightmoon.doraemonkit.R;
import com.ebrightmoon.doraemonkit.config.AlignRulerConfig;
import com.ebrightmoon.doraemonkit.constant.PageTag;
import com.ebrightmoon.doraemonkit.ui.base.BaseFragment;
import com.ebrightmoon.doraemonkit.ui.base.FloatPageManager;
import com.ebrightmoon.doraemonkit.ui.base.PageIntent;
import com.ebrightmoon.doraemonkit.ui.setting.SettingItem;
import com.ebrightmoon.doraemonkit.ui.setting.SettingItemAdapter;
import com.ebrightmoon.doraemonkit.ui.widget.titlebar.HomeTitleBar;

/**
 * Created by wanglikun on 2018/9/19.
 */

public class AlignRulerSettingFragment extends BaseFragment {
    private HomeTitleBar mTitleBar;
    private RecyclerView mSettingList;
    private SettingItemAdapter mSettingItemAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTitleBar();
    }

    private void initTitleBar() {
        mTitleBar = findViewById(R.id.title_bar);
        mTitleBar.setListener(new HomeTitleBar.OnTitleBarClickListener() {
            @Override
            public void onRightClick() {
                finish();
            }
        });
        mSettingList = findViewById(R.id.setting_list);
        mSettingList.setLayoutManager(new LinearLayoutManager(getContext()));
        mSettingItemAdapter = new SettingItemAdapter(getContext());
        mSettingItemAdapter.append(new SettingItem(R.string.dk_kit_align_ruler, AlignRulerConfig.isAlignRulerOpen(getContext())));
        mSettingList.setAdapter(mSettingItemAdapter);
        mSettingItemAdapter.setOnSettingItemSwitchListener(new SettingItemAdapter.OnSettingItemSwitchListener() {
            @Override
            public void onSettingItemSwitch(View view, SettingItem data, boolean on) {
                if (data.desc == R.string.dk_kit_align_ruler) {
                    if (on) {
                        PageIntent pageIntent = new PageIntent(AlignRulerMarkerFloatPage.class);
                        pageIntent.tag = PageTag.PAGE_ALIGN_RULER_MARKER;
                        FloatPageManager.getInstance().add(pageIntent);
                        FloatPageManager.getInstance().add(new PageIntent(AlignRulerLineFloatPage.class));
                    } else {
                        FloatPageManager.getInstance().removeAll(AlignRulerMarkerFloatPage.class);
                        FloatPageManager.getInstance().removeAll(AlignRulerLineFloatPage.class);
                    }
                    AlignRulerConfig.setAlignRulerOpen(getContext(), on);
                }
            }
        });
    }

    @Override
    protected int onRequestLayout() {
        return R.layout.dk_fragment_align_ruler_setting;
    }
}
