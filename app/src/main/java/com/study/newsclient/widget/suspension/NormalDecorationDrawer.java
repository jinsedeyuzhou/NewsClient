package com.study.newsclient.widget.suspension;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by lv on 17-10-9.
 */

public class NormalDecorationDrawer implements DecorationDrawer {
    @Override
    public void drawVertical(Canvas c, String title, float left, float top, float right, float bottom,
                             Paint mBackgroundPaint, Paint mTextPaint, float baseLine, int mTitleHeight) {

        c.drawRect(left, top, right, bottom, mBackgroundPaint);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        c.drawText(title, 50, baseLine, mTextPaint);
    }

    @Override
    public void drawFlow(Canvas c, String title, float left, float top, float right, float bottom,
                         Paint mBackgroundPaint, Paint mTextPaint, float baseLine, int mTitleHeight) {
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        c.drawRect(left, top, right, bottom, mBackgroundPaint);

        c.drawText(title, 50, baseLine, mTextPaint);
    }


}
