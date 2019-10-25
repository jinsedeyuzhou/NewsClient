package com.ebrightmoon.doraemonkit.kit.parameter.ram;

import android.content.Context;
import android.content.Intent;

import com.ebrightmoon.doraemonkit.R;
import com.ebrightmoon.doraemonkit.constant.BundleKey;
import com.ebrightmoon.doraemonkit.constant.FragmentIndex;
import com.ebrightmoon.doraemonkit.kit.Category;
import com.ebrightmoon.doraemonkit.kit.IKit;
import com.ebrightmoon.doraemonkit.ui.UniversalActivity;

public class Ram implements IKit {
    @Override
    public int getCategory() {
        return Category.PERFORMANCE;
    }

    @Override
    public int getName() {
        return R.string.dk_frameinfo_ram;
    }

    @Override
    public int getIcon() {
        return R.drawable.dk_ram;
    }

    @Override
    public void onClick(Context context) {
        Intent intent = new Intent(context, UniversalActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(BundleKey.FRAGMENT_INDEX, FragmentIndex.FRAGMENT_RAM);
        context.startActivity(intent);
    }

    @Override
    public void onAppInit(Context context) {

    }
}
