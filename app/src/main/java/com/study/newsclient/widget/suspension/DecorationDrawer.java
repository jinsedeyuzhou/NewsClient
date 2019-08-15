package com.study.newsclient.widget.suspension;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by lv on 17-10-9.
 */

public interface DecorationDrawer {
    /**
     * 绘制组头部
     */
    void drawVertical(Canvas c, String title, float left, float top, float right, float bottom,
                      Paint mBackgroundPaint, Paint mTextPaint, float baseLine, int mTitleHeight);

    /**
     * 绘制固定悬浮头部
     */
    void drawFlow(Canvas c, String title, float left, float top, float right, float bottom,
                  Paint mBackgroundPaint, Paint mTextPaint, float baseLine, int mTitleHeight);
}
