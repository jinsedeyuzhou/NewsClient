package com.yuxuan.commonutils.base;

import android.content.Context;

/**
 * Created by wyy on 2017/4/4.
 */

public class CommonApplication {

    private static Context mContext;
    private static CommonApplication mInstance;

    public static void initQDApp(Context context) {
        mContext = context;
        mInstance = new CommonApplication();
    }

    private CommonApplication() {
        mInstance = this;
    }

    public static Context getAppContext() {
        return mContext;
    }

    public static synchronized CommonApplication getInstance() {
        if (null == mInstance) {
            mInstance = new CommonApplication();
        }
        return mInstance;
    }


}
