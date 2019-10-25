package com.ebrightmoon.doraemonkit.kit.parameter;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;

import com.ebrightmoon.doraemonkit.R;
import com.ebrightmoon.doraemonkit.ui.base.BaseFragment;
import com.ebrightmoon.doraemonkit.ui.realtime.OnFloatPageChangeListener;
import com.ebrightmoon.doraemonkit.ui.realtime.RealTimeChartIconPage;
import com.ebrightmoon.doraemonkit.ui.realtime.RealTimeChartPage;
import com.ebrightmoon.doraemonkit.ui.setting.SettingItem;
import com.ebrightmoon.doraemonkit.ui.setting.SettingItemAdapter;
import com.ebrightmoon.doraemonkit.ui.widget.titlebar.HomeTitleBar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbsParameterFragment extends BaseFragment implements OnFloatPageChangeListener {


    private SettingItemAdapter mSettingItemAdapter;
    private RecyclerView mSettingList;
    private static final String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};
    private static final int REQUEST_EXTERNAL_STORAGE = 2;


    @Override
    protected int onRequestLayout() {
        return R.layout.dk_fragment_parameter;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @StringRes
    protected abstract int getTitle();

    protected abstract Collection<SettingItem> getSettingItems(List<SettingItem> list);

    protected abstract SettingItemAdapter.OnSettingItemSwitchListener getItemSwitchListener();

    protected abstract SettingItemAdapter.OnSettingItemClickListener getItemClickListener();

    protected void openChartPage(@StringRes int title, int type) {
        RealTimeChartPage.openChartPage(getString(title), type, RealTimeChartPage.DEFAULT_REFRESH_INTERVAL, this);
    }

    protected void closeChartPage() {
        RealTimeChartPage.closeChartPage();
    }

    private void initView() {
        HomeTitleBar titleBar = findViewById(R.id.title_bar);
        titleBar.setTitle(getTitle());
        titleBar.setListener(new HomeTitleBar.OnTitleBarClickListener() {
            @Override
            public void onRightClick() {
                getActivity().finish();
            }
        });

        mSettingList = findViewById(R.id.setting_list);
        mSettingList.setLayoutManager(new LinearLayoutManager(getContext()));
        mSettingItemAdapter = new SettingItemAdapter(getContext());
        mSettingItemAdapter.append(getSettingItems(new ArrayList<SettingItem>()));

        mSettingItemAdapter.setOnSettingItemSwitchListener(new SettingItemAdapter.OnSettingItemSwitchListener() {
            @Override
            public void onSettingItemSwitch(View view, SettingItem data, boolean on) {
                if (on && !ownPermissionCheck()) {
                    if (view instanceof CheckBox) {
                        ((CheckBox) view).setChecked(false);
                    }
                    requestPermissions(PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
                    return;
                }
                SettingItemAdapter.OnSettingItemSwitchListener itemSwitchListener = getItemSwitchListener();
                if (itemSwitchListener != null) {
                    itemSwitchListener.onSettingItemSwitch(view, data, on);
                }
            }
        });
        mSettingItemAdapter.setOnSettingItemClickListener(new SettingItemAdapter.OnSettingItemClickListener() {
            @Override
            public void onSettingItemClick(View view, SettingItem data) {
                if (!ownPermissionCheck()) {
                    requestPermissions(PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
                    return;
                }
                SettingItemAdapter.OnSettingItemClickListener itemClickListener = getItemClickListener();
                if (itemClickListener != null) {
                    itemClickListener.onSettingItemClick(view, data);
                }

            }
        });
        mSettingList.setAdapter(mSettingItemAdapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            for (int grantResult : grantResults) {
                if (grantResult == -1) {
                    showToast(R.string.dk_error_tips_permissions_less);
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private boolean ownPermissionCheck() {

        int permission = ActivityCompat.checkSelfPermission(getActivity(), "android.permission.WRITE_EXTERNAL_STORAGE");
        if (permission != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    @Override
    public void onFloatPageClose(String tag) {
        if (!TextUtils.equals(RealTimeChartIconPage.TAG, tag)) {
            return;
        }
        if (mSettingList == null || mSettingList.isComputingLayout()) {
            return;
        }
        if (mSettingItemAdapter == null) {
            return;
        }
        if (!mSettingItemAdapter.getData().get(0).isChecked) {
            return;
        }
        mSettingItemAdapter.getData().get(0).isChecked = false;
        mSettingItemAdapter.notifyItemChanged(0);
    }

    @Override
    public void onFloatPageOpen(String tag) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        RealTimeChartPage.removeCloseListener();
    }
}
