package com.ebrightmoon.doraemonkit.ui.widget.tableview.intface;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.ebrightmoon.doraemonkit.ui.widget.tableview.bean.CellInfo;
import com.ebrightmoon.doraemonkit.ui.widget.tableview.bean.Column;


public interface IDrawFormat<T> {

    /**
     * 测量宽
     */
    int measureWidth(Column<T> column, int position);

    /**
     * 测量高
     */
    int measureHeight(Column<T> column, int position);


    void draw(Canvas c, Rect rect, CellInfo<T> cellInfo);


}
