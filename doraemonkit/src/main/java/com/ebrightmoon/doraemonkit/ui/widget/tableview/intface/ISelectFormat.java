package com.ebrightmoon.doraemonkit.ui.widget.tableview.intface;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.ebrightmoon.doraemonkit.ui.widget.tableview.TableConfig;

public interface ISelectFormat {

    void draw(Canvas canvas, Rect rect, Rect showRect, TableConfig config);
}
