package com.ebrightmoon.doraemonkit.kit.weaknetwork;

import android.content.Context;
import android.content.Intent;

import com.ebrightmoon.doraemonkit.R;
import com.ebrightmoon.doraemonkit.constant.BundleKey;
import com.ebrightmoon.doraemonkit.constant.FragmentIndex;
import com.ebrightmoon.doraemonkit.kit.Category;
import com.ebrightmoon.doraemonkit.kit.IKit;
import com.ebrightmoon.doraemonkit.ui.UniversalActivity;

/**
 * 模拟弱网
 *
 * @author denghaha
 * created 2019/5/7 19:05
 */
public class WeakNetwork implements IKit {
    @Override
    public int getCategory() {
        return Category.TOOLS;
    }

    @Override
    public int getName() {
        return R.string.dk_kit_weak_network;
    }

    @Override
    public int getIcon() {
        return R.drawable.dk_weak_network;
    }

    @Override
    public void onClick(Context context) {
        Intent intent = new Intent(context, UniversalActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(BundleKey.FRAGMENT_INDEX, FragmentIndex.FRAGMENT_WEAK_NETWORK);
        context.startActivity(intent);
    }

    @Override
    public void onAppInit(Context context) {

    }
}