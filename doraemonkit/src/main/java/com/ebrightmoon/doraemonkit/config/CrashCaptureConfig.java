package com.ebrightmoon.doraemonkit.config;

import android.content.Context;

import com.ebrightmoon.doraemonkit.constant.SharedPrefsKey;
import com.ebrightmoon.doraemonkit.util.SharedPrefsUtil;

public class CrashCaptureConfig {
    public static boolean isCrashCaptureOpen(Context context) {
        return SharedPrefsUtil.getBoolean(context, SharedPrefsKey.CRASH_CAPTURE_OPEN, false);
    }

    public static void setCrashCaptureOpen(Context context, boolean open) {
        SharedPrefsUtil.putBoolean(context, SharedPrefsKey.CRASH_CAPTURE_OPEN, open);
    }
}
