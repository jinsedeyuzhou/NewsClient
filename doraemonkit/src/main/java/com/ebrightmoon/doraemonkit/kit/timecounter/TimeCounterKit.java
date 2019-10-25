package com.ebrightmoon.doraemonkit.kit.timecounter;

import android.content.Context;
import android.content.Intent;

import com.ebrightmoon.doraemonkit.R;
import com.ebrightmoon.doraemonkit.constant.BundleKey;
import com.ebrightmoon.doraemonkit.constant.FragmentIndex;
import com.ebrightmoon.doraemonkit.kit.Category;
import com.ebrightmoon.doraemonkit.kit.IKit;
import com.ebrightmoon.doraemonkit.ui.UniversalActivity;

/**
 * app启动、页面跳转的计时kit
 */

public class TimeCounterKit implements IKit {
    @Override
    public int getCategory() {
        return Category.TOOLS;
    }

    @Override
    public int getName() {
        return R.string.dk_kit_time_counter;
    }

    @Override
    public int getIcon() {
        return R.drawable.dk_time_counter;
    }

    @Override
    public void onClick(Context context) {
        Intent intent = new Intent(context, UniversalActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(BundleKey.FRAGMENT_INDEX, FragmentIndex.FRAGMENT_TIME_COUNTER);
        context.startActivity(intent);
    }

    @Override
    public void onAppInit(Context context) {

    }

}
