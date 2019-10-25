package com.ebrightmoon.doraemonkit.kit.alignruler;

import android.content.Context;

import com.ebrightmoon.doraemonkit.R;
import com.ebrightmoon.doraemonkit.config.AlignRulerConfig;
import com.ebrightmoon.doraemonkit.constant.PageTag;
import com.ebrightmoon.doraemonkit.kit.Category;
import com.ebrightmoon.doraemonkit.kit.IKit;
import com.ebrightmoon.doraemonkit.ui.base.FloatPageManager;
import com.ebrightmoon.doraemonkit.ui.base.PageIntent;

/**
 * Created by wanglikun on 2018/9/19.
 */

public class AlignRuler implements IKit {
    @Override
    public int getCategory() {
        return Category.UI;
    }

    @Override
    public int getName() {
        return R.string.dk_kit_align_ruler;
    }

    @Override
    public int getIcon() {
        return R.drawable.dk_align_ruler;
    }

    @Override
    public void onClick(Context context) {
        PageIntent pageIntent = new PageIntent(AlignRulerMarkerFloatPage.class);
        pageIntent.tag = PageTag.PAGE_ALIGN_RULER_MARKER;
        pageIntent.mode=PageIntent.MODE_SINGLE_INSTANCE;
        FloatPageManager.getInstance().add(pageIntent);

        pageIntent=new PageIntent(AlignRulerLineFloatPage.class);
        pageIntent.mode=PageIntent.MODE_SINGLE_INSTANCE;
        FloatPageManager.getInstance().add(pageIntent);

        pageIntent=new PageIntent(AlignRulerInfoFloatPage.class);
        pageIntent.mode=PageIntent.MODE_SINGLE_INSTANCE;
        FloatPageManager.getInstance().add(pageIntent);

        AlignRulerConfig.setAlignRulerOpen(context, true);
    }

    @Override
    public void onAppInit(Context context) {
        AlignRulerConfig.setAlignRulerOpen(context, false);
    }
}
