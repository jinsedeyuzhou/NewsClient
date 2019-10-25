package com.ebrightmoon.doraemonkit.ui.widget.tableview.intface;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.ebrightmoon.doraemonkit.ui.widget.tableview.bean.Column;
import com.ebrightmoon.doraemonkit.ui.widget.tableview.TableConfig;



public interface ITitleDrawFormat {

    /**
     *测量宽
     */
    int measureWidth(Column column, TableConfig config);

    /**
     *测量高
     */
    int measureHeight(TableConfig config);

    /**
     * 绘制
     * @param c 画笔
     * @param column 列信息
     */
    void draw(Canvas c, Column column, Rect rect, TableConfig config);


}
