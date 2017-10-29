package com.study.newsclient.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.study.newsclient.R;


/**
 * 自定义圆形进度条
 * 可以设置内圆，外圆，进度体，字体的颜色和大小，进度条宽度
 * Created by Berkeley on 8/3/17.
 */

public class CustomCircleProgress extends View {


    private int mCircleColor;
    private int mCircleProgressColor;
    private float mCirclrWidth;
    private int mTextColor;
    private float mTextSize;
    private int max;
    private boolean mTextIsDisplayable;
    private int style;
    private Paint mPaint;
    private int progress = 0;
    public static final int STROKE = 0;
    public static final int FILL = 1;
    private float mCirclRadius;
    private float mRingRadius;
    private int mCircleInnerColor;
    private int mTxtHeight;

    public CustomCircleProgress(Context context) {
        super(context);
    }

    public CustomCircleProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomCircleProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint();
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomCircleProgress);
        mCircleColor = mTypedArray.getColor(R.styleable.CustomCircleProgress_circleColor, Color.LTGRAY);
        mCircleInnerColor = mTypedArray.getColor(R.styleable.CustomCircleProgress_circleInnerColor, Color.WHITE);
        mCircleProgressColor = mTypedArray.getColor(R.styleable.CustomCircleProgress_circleProgressColor, Color.GRAY);
        mCirclrWidth = mTypedArray.getDimension(R.styleable.CustomCircleProgress_circleWidth, 5);
        mCirclRadius = mTypedArray.getDimension(R.styleable.CustomCircleProgress_circleRadius, 5);
        mTextColor = mTypedArray.getColor(R.styleable.CustomCircleProgress_textColor, Color.GRAY);
        mTextSize = mTypedArray.getDimension(R.styleable.CustomCircleProgress_textSize, 16);
        max = mTypedArray.getInt(R.styleable.CustomCircleProgress_max, 100);
        mTextIsDisplayable = mTypedArray.getBoolean(R.styleable.CustomCircleProgress_textIsDisplayable, true);
        style = mTypedArray.getInt(R.styleable.CustomCircleProgress_style, 0);
        mTypedArray.recycle();


        Paint.FontMetrics fm = mPaint.getFontMetrics();
        mTxtHeight = (int) Math.ceil(fm.descent - fm.ascent);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int centre = getWidth() / 2;
        int radius = (int) (centre - mCirclrWidth / 2);
        mPaint.setColor(mCircleInnerColor);
//        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        canvas.drawCircle(centre, centre, radius-mCirclrWidth/2, mPaint);

        mPaint.setColor(mCircleColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mCirclrWidth);
        canvas.drawCircle(centre, centre, radius, mPaint);

        mPaint.setStrokeWidth(0);
        mPaint.setColor(mTextColor);
        mPaint.setTextSize(mTextSize);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        int percent = (int) (((float) progress / (float) max) * 100);
        float textWidth = mPaint.measureText(percent + "%");   //测量字体宽度，我们需要根据字体的宽度设置在圆环中间
        if (mTextIsDisplayable && percent != 0 && style == STROKE) {
            canvas.drawText(percent + "%", centre - textWidth / 2, centre + mTextSize / 2, mPaint); //画出进度百分比
        }

        mPaint.setStrokeWidth(mCirclrWidth);
        mPaint.setColor(mCircleProgressColor);
        RectF oval = new RectF(centre - radius, centre - radius, centre
                + radius, centre + radius);
        switch (style) {
            case STROKE: {
                mPaint.setStyle(Paint.Style.STROKE);
                canvas.drawArc(oval, 0, 360 * progress / max, false, mPaint);
                break;
            }
            case FILL: {
                mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                if (progress != 0) {
                    canvas.drawArc(oval, 0, 360 * progress / max, false, mPaint);
                }
                break;
            }
        }

    }

    public synchronized void setMax(int max) {
        if (max < 0) {
            throw new IllegalArgumentException("max not less than 0");
        }
        this.max = max;
    }

    public synchronized int getMax() {
        return max;
    }


    public synchronized int getProgress() {
        return progress;
    }

    public synchronized void setProgress(int progress) {
        if (progress < 0) {
            throw new IllegalArgumentException("progress not than 0");
        }
        if (progress > max) {
            progress = max;
        }
        if (progress <= max) {
            this.progress = progress;
            postInvalidate();

        }
    }


    public int getmCircleColor() {
        return mCircleColor;
    }

    public void setmCircleColor(int mCircleColor) {
        this.mCircleColor = mCircleColor;
    }

    public int getmCircleProgressColor() {
        return mCircleProgressColor;
    }

    public void setmCircleProgressColor(int mCircleProgressColor) {
        this.mCircleProgressColor = mCircleProgressColor;
    }

    public float getmCirclrWidth() {
        return mCirclrWidth;
    }

    public void setmCirclrWidth(float mCirclrWidth) {
        this.mCirclrWidth = mCirclrWidth;
    }

    public int getmTextColor() {
        return mTextColor;
    }

    public void setmTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
    }

    public float getmTextSize() {
        return mTextSize;
    }

    public void setmTextSize(float mTextSize) {
        this.mTextSize = mTextSize;
    }

    public boolean ismTextIsDisplayable() {
        return mTextIsDisplayable;
    }

    public void setmTextIsDisplayable(boolean mTextIsDisplayable) {
        this.mTextIsDisplayable = mTextIsDisplayable;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }


}
