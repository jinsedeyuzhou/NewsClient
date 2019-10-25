package com.ebrightmoon.doraemonkit.kit.parameter.frameInfo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import com.ebrightmoon.doraemonkit.R;
import com.ebrightmoon.doraemonkit.constant.BundleKey;
import com.ebrightmoon.doraemonkit.kit.common.PerformanceDataManager;
import com.ebrightmoon.doraemonkit.kit.common.PerformanceFragment;
import com.ebrightmoon.doraemonkit.kit.parameter.AbsParameterFragment;
import com.ebrightmoon.doraemonkit.ui.realtime.datasource.DataSourceFactory;
import com.ebrightmoon.doraemonkit.ui.setting.SettingItem;
import com.ebrightmoon.doraemonkit.ui.setting.SettingItemAdapter;
import java.util.Collection;
import java.util.List;

/**
 * Created by wanglikun on 2018/9/13.
 */

public class FrameInfoFragment extends AbsParameterFragment {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PerformanceDataManager.getInstance().init(getContext());
    }

    @Override
    protected int getTitle() {
        return R.string.dk_kit_frame_info_desc;
    }

    @Override
    protected Collection<SettingItem> getSettingItems(List<SettingItem> list) {
        list.add(new SettingItem(R.string.dk_frameinfo_detection_switch, false));
        list.add(new SettingItem(R.string.dk_item_cache_log, R.drawable.dk_more_icon));
        return list;
    }

    @Override
    protected SettingItemAdapter.OnSettingItemSwitchListener getItemSwitchListener() {
        return new SettingItemAdapter.OnSettingItemSwitchListener() {
            @Override
            public void onSettingItemSwitch(View view, SettingItem data, boolean on) {
                if (on) {
                    startMonitor();
                } else {
                    stopMonitor();
                }
//                PerformanceInfoConfig.setFPSOpen(getContext(), on);
            }
        };
    }

    @Override
    protected SettingItemAdapter.OnSettingItemClickListener getItemClickListener() {
        return new SettingItemAdapter.OnSettingItemClickListener() {
            @Override
            public void onSettingItemClick(View view, SettingItem data) {
                if (data.desc == R.string.dk_item_cache_log) {

                    Bundle bundle = new Bundle();
                    bundle.putInt(BundleKey.PERFORMANCE_TYPE, PerformanceFragment.FPS);
                    showContent(PerformanceFragment.class, bundle);
                }

            }
        };
    }

    private void startMonitor() {
        PerformanceDataManager.getInstance().startMonitorFrameInfo();
        openChartPage(R.string.dk_frameinfo_fps, DataSourceFactory.TYPE_FRAME);
    }

    private void stopMonitor() {
        PerformanceDataManager.getInstance().stopMonitorFrameInfo();
        closeChartPage();
    }

}
