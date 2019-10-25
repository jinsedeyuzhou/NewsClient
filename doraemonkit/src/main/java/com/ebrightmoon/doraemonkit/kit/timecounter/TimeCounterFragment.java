package com.ebrightmoon.doraemonkit.kit.timecounter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ebrightmoon.doraemonkit.R;
import com.ebrightmoon.doraemonkit.ui.base.BaseFragment;
import com.ebrightmoon.doraemonkit.ui.setting.SettingItem;
import com.ebrightmoon.doraemonkit.ui.setting.SettingItemAdapter;
import com.ebrightmoon.doraemonkit.ui.widget.titlebar.HomeTitleBar;

/**
 * @desc: Activity跳转耗时检测首页
 */

public class TimeCounterFragment extends BaseFragment {

    @Override
    protected int onRequestLayout() {
        return R.layout.dk_fragment_time_counter_index;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }


    private void initView() {
        HomeTitleBar title = findViewById(R.id.title_bar);
        title.setListener(new HomeTitleBar.OnTitleBarClickListener() {
            @Override
            public void onRightClick() {
                getActivity().finish();
            }
        });
        RecyclerView mSettingList = findViewById(R.id.setting_list);
        mSettingList.setLayoutManager(new LinearLayoutManager(getContext()));
        SettingItemAdapter settingItemAdapter = new SettingItemAdapter(getContext());
        mSettingList.setAdapter(settingItemAdapter);
        settingItemAdapter.append(new SettingItem(R.string.dk_item_time_counter_switch, TimeCounterManager.get().isRunning()));
        settingItemAdapter.append(new SettingItem(R.string.dk_item_time_goto_list));

        settingItemAdapter.setOnSettingItemSwitchListener(new SettingItemAdapter.OnSettingItemSwitchListener() {
            @Override
            public void onSettingItemSwitch(View view, SettingItem data, boolean on) {
                if (data.desc == R.string.dk_item_time_counter_switch) {
                    if (on) {
                        TimeCounterManager.get().start();
                    } else {
                        TimeCounterManager.get().stop();
                    }
                }
            }
        });
        settingItemAdapter.setOnSettingItemClickListener(new SettingItemAdapter.OnSettingItemClickListener() {
            @Override
            public void onSettingItemClick(View view, SettingItem data) {
                if (data.desc == R.string.dk_item_time_goto_list) {
                    showContent(TimeCounterListFragment.class);
                }
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}