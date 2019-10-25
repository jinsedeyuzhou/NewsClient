package com.ebrightmoon.doraemonkit.kit.blockmonitor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ebrightmoon.doraemonkit.R;
import com.ebrightmoon.doraemonkit.kit.blockmonitor.core.BlockMonitorManager;
import com.ebrightmoon.doraemonkit.ui.base.BaseFragment;
import com.ebrightmoon.doraemonkit.ui.setting.SettingItem;
import com.ebrightmoon.doraemonkit.ui.setting.SettingItemAdapter;
import com.ebrightmoon.doraemonkit.ui.widget.titlebar.HomeTitleBar;

/**
 * @desc: 卡顿检测首页
 */

public class BlockMonitorFragment extends BaseFragment {
    private static final String TAG = "BlockMonitorIndexFragment";
    public static final String KEY_JUMP_TO_LIST = "KEY_JUMP_TO_LIST";

    @Override
    protected int onRequestLayout() {
        return R.layout.dk_fragment_block_monitor_index;
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
        settingItemAdapter.append(new SettingItem(R.string.dk_item_block_switch, BlockMonitorManager.getInstance().isRunning()));
        settingItemAdapter.append(new SettingItem(R.string.dk_item_block_goto_list));
        settingItemAdapter.append(new SettingItem(R.string.dk_item_block_mock));

        settingItemAdapter.setOnSettingItemSwitchListener(new SettingItemAdapter.OnSettingItemSwitchListener() {
            @Override
            public void onSettingItemSwitch(View view, SettingItem data, boolean on) {
                if (data.desc == R.string.dk_item_block_switch) {
                    if (on) {
                        BlockMonitorManager.getInstance().start(getContext());
                    } else {
                        BlockMonitorManager.getInstance().stop();
                    }
                }
            }
        });
        settingItemAdapter.setOnSettingItemClickListener(new SettingItemAdapter.OnSettingItemClickListener() {
            @Override
            public void onSettingItemClick(View view, SettingItem data) {
                if (data.desc == R.string.dk_item_block_goto_list) {
                    showContent(BlockListFragment.class);
                } else if (data.desc == R.string.dk_item_block_mock) {
                    mockBlock();
                }
            }
        });

        if (getArguments() != null) {
            boolean jump = getArguments().getBoolean(KEY_JUMP_TO_LIST, false);
            if (jump) {
                showContent(BlockListFragment.class);
            }
        }
    }

    private void mockBlock() {
        try {
            getView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, 1000);
        } catch (Exception e) {

        }
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