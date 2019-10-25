package com.ebrightmoon.doraemonkit.config;

import android.content.Context;

import com.ebrightmoon.doraemonkit.constant.SharedPrefsKey;
import com.ebrightmoon.doraemonkit.util.SharedPrefsUtil;

/**
 * Created by wanglikun on 2018/12/28
 */
public class ViewCheckConfig {
    public static boolean isViewCheckOpen(Context context) {
        return SharedPrefsUtil.getBoolean(context, SharedPrefsKey.VIEW_CHECK_OPEN, false);
    }

    public static void setViewCheckOpen(Context context, boolean open) {
        SharedPrefsUtil.putBoolean(context, SharedPrefsKey.VIEW_CHECK_OPEN, open);
    }
}
