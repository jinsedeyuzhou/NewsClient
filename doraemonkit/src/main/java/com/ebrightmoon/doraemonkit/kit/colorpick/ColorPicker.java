package com.ebrightmoon.doraemonkit.kit.colorpick;

import android.content.Context;
import android.content.Intent;

import com.ebrightmoon.doraemonkit.R;
import com.ebrightmoon.doraemonkit.config.ColorPickConfig;
import com.ebrightmoon.doraemonkit.constant.BundleKey;
import com.ebrightmoon.doraemonkit.constant.FragmentIndex;
import com.ebrightmoon.doraemonkit.kit.Category;
import com.ebrightmoon.doraemonkit.kit.IKit;
import com.ebrightmoon.doraemonkit.ui.TranslucentActivity;

/**
 * Created by wanglikun on 2018/9/13.
 */

public class ColorPicker implements IKit {
    private static final String TAG = "ColorPicker";

    @Override
    public int getCategory() {
        return Category.UI;
    }

    @Override
    public int getName() {
        return R.string.dk_kit_color_picker;
    }

    @Override
    public int getIcon() {
        return R.drawable.dk_color_picker;
    }

    @Override
    public void onClick(Context context) {
        Intent intent = new Intent(context, TranslucentActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(BundleKey.FRAGMENT_INDEX, FragmentIndex.FRAGMENT_COLOR_PICKER_SETTING);
        context.startActivity(intent);
    }

    @Override
    public void onAppInit(Context context) {
        ColorPickConfig.setColorPickOpen(context, false);
    }
}