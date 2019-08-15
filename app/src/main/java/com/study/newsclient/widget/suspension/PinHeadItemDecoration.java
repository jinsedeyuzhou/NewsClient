package com.study.newsclient.widget.suspension;

/**
 * Created by lv on 17-8-11.
 */

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by  on 2017/10/7
 * E-Mail：
 * PInHeadItemDecoration floatingItemDecoration = new PInHeadItemDecoration();
 *         recyclerView.addItemDecoration(floatingItemDecoration);
 *         floatingItemDecoration
 *                 .bgColor(context.getResources().getColor(R.color.hint_bg))//设置头部背景颜色
 *                 .textSize(30 * WindowUtil.csw / WindowUtil.width)//设置画笔字体大小
 *                 .titleHeight(40 * WindowUtil.csw / WindowUtil.width);//设置悬浮头部的高度
 */

public class PinHeadItemDecoration extends RecyclerView.ItemDecoration {
    //存放拥有头部的位置及展示文字
    private Map<Integer, String> keys = new HashMap<>();
    //头部所占高度
    private int mTitleHeight = 100;
    //绘制文字的画笔
    private Paint mTextPaint;
    //绘制头部背景的画笔
    private Paint mBackgroundPaint;
    //文字高度
    private float mTextHeight;
    //文字基线位置
    private float mTextBaselineOffset;
    //悬浮头推动效果
    private boolean isPush = true;
    //滚动列表的时候是否一直显示悬浮头部
    private boolean showFloatingHeaderOnScrolling = true;
    private DecorationDrawer decorationDrawer;
    private float mTextCenterYToBottom;
    private float centerBaseLineHeight;

    public PinHeadItemDecoration() {
        init();
    }

    private void init() {
        decorationDrawer = new NormalDecorationDrawer();
        //文字画笔初始化
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(40);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setColor(Color.WHITE);
        //计算文字高度及基线位置
        Paint.FontMetrics fm = mTextPaint.getFontMetrics();
        mTextHeight = fm.bottom - fm.top;
        centerBaseLineHeight = (fm.bottom - fm.top) / 2 - fm.bottom;
        mTextBaselineOffset = fm.bottom;
        //获取文字的宽高
        mTextCenterYToBottom = mTitleHeight / 2 - centerBaseLineHeight;
        ;
        //背景画笔初始化
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setColor(Color.MAGENTA);
    }

    public PinHeadItemDecoration bgColor(@ColorInt int color) {
        mBackgroundPaint.setColor(color);
        return this;
    }

    public PinHeadItemDecoration setPush(boolean push) {
        isPush = push;
        return this;
    }

    public PinHeadItemDecoration textColor(@ColorInt int color) {
        mTextPaint.setColor(color);
        return this;
    }

    public PinHeadItemDecoration textSize(int px) {
        mTextPaint.setTextSize(px);
        //计算文字高度及基线位置
        Paint.FontMetrics fm = mTextPaint.getFontMetrics();
        mTextHeight = fm.bottom - fm.top;
        centerBaseLineHeight = (fm.bottom - fm.top) / 2 - fm.bottom;
        mTextBaselineOffset = fm.bottom;
        //获取文字的宽高
        mTextCenterYToBottom = mTitleHeight / 2 - centerBaseLineHeight;
        return this;
    }

    public PinHeadItemDecoration titleHeight(int height) {
        mTitleHeight = height;
        mTextCenterYToBottom = mTitleHeight / 2 -
                centerBaseLineHeight;
        return this;
    }

    public PinHeadItemDecoration setDecorationDrawer(DecorationDrawer decorationDrawer) {
        this.decorationDrawer = decorationDrawer;
        return this;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int firstVisiblePos = ((LinearLayoutManager) parent.getLayoutManager()).findFirstVisibleItemPosition();
        if (firstVisiblePos == RecyclerView.NO_POSITION) {//没有条目,返回
            return;
        }
        drawVertical(c, parent);//绘制移动头部
        if (!showFloatingHeaderOnScrolling) {//不显示浮动头部,返回
            return;
        }
        String title = getTitle(firstVisiblePos);
        if (TextUtils.isEmpty(title)) {//首个移动头目还未在顶部,不绘制,返回
            return;
        }
        int nextSecPos = getNextSecPos(firstVisiblePos);
        if (nextSecPos == -1) {
            drawFlowHead(c, parent, title, mTitleHeight);
        } else {
            RecyclerView.ViewHolder viewHolder = parent.findViewHolderForAdapterPosition(nextSecPos);
            if (viewHolder == null) {//下一个移动头目还未出现在屏幕上
                return;
            }
            View child = viewHolder.itemView;

            if (child.getTop() > mTitleHeight * 2) {
                drawFlowHead(c, parent, title, mTitleHeight);
            } else {
                drawFlowHead(c, parent, title, isPush ? child.getTop() - mTitleHeight : mTitleHeight);

            }
        }
    }

    private void drawFlowHead(Canvas c, RecyclerView parent, String title, int height) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int top = parent.getPaddingTop();
        int bottom = top + height;
        float baseLine = bottom - (mTitleHeight - mTextHeight) / 2 - mTextBaselineOffset;//计算文字baseLine
        decorationDrawer.drawFlow(c, title, left, top, right, bottom, mBackgroundPaint, mTextPaint, bottom - mTextCenterYToBottom, mTitleHeight);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int pos = parent.getChildViewHolder(view).getAdapterPosition();
        if (keys.containsKey(pos)) {//留出头部偏移
            outRect.set(0, mTitleHeight, 0, 0);
        }
    }

    /**
     * *如果该位置没有，则往前循环去查找标题，找到说明该位置属于该分组
     *
     * @param position
     * @return
     */
    private String getTitle(int position) {
        while (position >= 0) {
            if (keys.containsKey(position)) {
                return keys.get(position);
            }
            position--;
        }
        return null;
    }

    /**
     * *获取当前位置下一组的位置
     *
     * @param position
     * @return
     */
    private int getNextSecPos(int position) {
        Iterator<Map.Entry<Integer, String>> iterator = keys.entrySet().iterator();
        int maxPos = 0;
        while (iterator.hasNext()) {
            Integer key = iterator.next().getKey();
            if (key > maxPos) {
                maxPos = key;
            }
        }
        while (position <= maxPos) {
            position++;
            if (keys.containsKey(position)) {
                return position;
            }
        }
        return -1;
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int top;
        int bottom;
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            if (keys.containsKey(params.getViewLayoutPosition())) {

                top = child.getTop() - params.topMargin - mTitleHeight;
                bottom = top + mTitleHeight;
                float baseLine = bottom - (mTitleHeight - mTextHeight) / 2 - mTextBaselineOffset;//计算文字baseLine
                decorationDrawer.drawVertical(c, keys.get(params.getViewLayoutPosition()), left, top, right, bottom, mBackgroundPaint,
                        mTextPaint, bottom - mTextCenterYToBottom, mTitleHeight);
            }
        }
    }

    public void show(boolean isShow) {
        this.showFloatingHeaderOnScrolling = isShow;
    }

    public void setKeys(Map<Integer, String> keys) {
        this.keys.clear();
        this.keys.putAll(keys);
    }

}