package com.yuxuan.common.util;

import android.util.Log;

/**
 *开发阶段将下面LOG_LEVEL 设置为6这样所有的log都能显示， 在发布的时候我们将LOG_LEVEL 设置为0.这样log就非常方便管理了
 *该类用于检测程序运行中出现的不定异常错误，方便定位
 * Created by wyy on 2016/9/11.
 */
public class LogUtils {
    public static int LOG_LEVEL = 6;
    public static int ERROR = 1;
    public static int WARN = 2;
    public static int INFO = 3;
    public static int DEBUG = 4;
    public static int VERBOS = 5;



    public static void e(String tag, String msg) {
        if (LOG_LEVEL > ERROR)
            Log.e(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (LOG_LEVEL > WARN)
            Log.w(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (LOG_LEVEL > INFO)
            Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (LOG_LEVEL > DEBUG)
            Log.d(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (LOG_LEVEL > VERBOS)
            Log.v(tag, msg);
    }
}
