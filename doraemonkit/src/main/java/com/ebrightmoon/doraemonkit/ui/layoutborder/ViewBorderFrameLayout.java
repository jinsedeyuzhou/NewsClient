package com.ebrightmoon.doraemonkit.ui.layoutborder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ebrightmoon.doraemonkit.config.LayoutBorderConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanglikun on 2019/1/12
 */
public class ViewBorderFrameLayout extends FrameLayout {
    public ViewBorderFrameLayout(@NonNull Context context) {
        super(context);
    }

    public ViewBorderFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewBorderFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (LayoutBorderConfig.isLayoutBorderOpen()) {
            traverseChild(this);
        } else {
            clearChild(this);
        }
    }

    private void traverseChild(View view) {
        if (view instanceof ViewGroup) {
            replaceDrawable(view);
            int childCount = ((ViewGroup) view).getChildCount();
            if (childCount != 0) {
                for (int index = 0; index < childCount; index++) {
                    traverseChild(((ViewGroup) view).getChildAt(index));
                }
            }
        } else {
            replaceDrawable(view);
        }
    }

    private void replaceDrawable(View view) {
        if (view instanceof TextureView) {
            // 过滤TextureView
            return;
        }
        LayerDrawable newDrawable;
        if (view.getBackground() != null) {
            Drawable oldDrawable = view.getBackground();
            if (oldDrawable instanceof LayerDrawable) {
                for (int i = 0; i < ((LayerDrawable) oldDrawable).getNumberOfLayers(); i++) {
                    if (((LayerDrawable) oldDrawable).getDrawable(i) instanceof ViewBorderDrawable) {
                        // already replace
                        return;
                    }
                }
                newDrawable = new LayerDrawable(new Drawable[]{
                        oldDrawable,
                        new ViewBorderDrawable(view)
                });
            } else {
                newDrawable = new LayerDrawable(new Drawable[]{
                        oldDrawable,
                        new ViewBorderDrawable(view)
                });
            }
        } else {
            newDrawable = new LayerDrawable(new Drawable[]{
                    new ViewBorderDrawable(view)
            });
        }
        try {
            view.setBackground(newDrawable);
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        }
    }

    private void clearChild(View view) {
        if (view instanceof ViewGroup) {
            clearDrawable(view);
            int childCount = ((ViewGroup) view).getChildCount();
            if (childCount != 0) {
                for (int index = 0; index < childCount; index++) {
                    clearChild(((ViewGroup) view).getChildAt(index));
                }
            }
        } else {
            clearDrawable(view);
        }
    }

    private void clearDrawable(View view) {
        if (view.getBackground() == null) {
            return;
        }
        Drawable oldDrawable = view.getBackground();
        if (!(oldDrawable instanceof LayerDrawable)) {
            return;
        }
        LayerDrawable layerDrawable = (LayerDrawable) oldDrawable;
        List<Drawable> drawables = new ArrayList<>();
        for (int i = 0; i < layerDrawable.getNumberOfLayers(); i++) {
            if (layerDrawable.getDrawable(i) instanceof ViewBorderDrawable) {
                continue;
            }
            drawables.add(layerDrawable.getDrawable(i));
        }
        LayerDrawable newDrawable = new LayerDrawable(drawables.toArray(new Drawable[drawables.size()]));
        view.setBackground(newDrawable);
    }
}