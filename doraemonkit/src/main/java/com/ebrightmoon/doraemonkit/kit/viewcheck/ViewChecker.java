package com.ebrightmoon.doraemonkit.kit.viewcheck;

import android.content.Context;

import com.ebrightmoon.doraemonkit.R;
import com.ebrightmoon.doraemonkit.config.ViewCheckConfig;
import com.ebrightmoon.doraemonkit.constant.PageTag;
import com.ebrightmoon.doraemonkit.kit.Category;
import com.ebrightmoon.doraemonkit.kit.IKit;
import com.ebrightmoon.doraemonkit.ui.base.FloatPageManager;
import com.ebrightmoon.doraemonkit.ui.base.PageIntent;

/**
 * Created by wanglikun on 2018/11/20.
 */

public class ViewChecker implements IKit {
    @Override
    public int getCategory() {
        return Category.UI;
    }

    @Override
    public int getName() {
        return R.string.dk_kit_view_check;
    }

    @Override
    public int getIcon() {
        return R.drawable.dk_view_check;
    }

    @Override
    public void onClick(Context context) {
        PageIntent intent = new PageIntent(ViewCheckFloatPage.class);
        intent.tag = PageTag.PAGE_VIEW_CHECK;
        intent.mode = PageIntent.MODE_SINGLE_INSTANCE;
        FloatPageManager.getInstance().add(intent);

        intent = new PageIntent(ViewCheckInfoFloatPage.class);
        intent.mode = PageIntent.MODE_SINGLE_INSTANCE;
        FloatPageManager.getInstance().add(intent);

        intent = new PageIntent(ViewCheckDrawFloatPage.class);
        intent.mode = PageIntent.MODE_SINGLE_INSTANCE;
        FloatPageManager.getInstance().add(intent);

        ViewCheckConfig.setViewCheckOpen(context, true);
    }

    @Override
    public void onAppInit(Context context) {
        ViewCheckConfig.setViewCheckOpen(context, false);
    }
}