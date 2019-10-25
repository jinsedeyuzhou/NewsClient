package com.ebrightmoon.doraemonkit.ui.widget.tableview.intface;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.ebrightmoon.doraemonkit.ui.widget.tableview.TableConfig;

public interface ISequenceFormat extends IFormat<Integer> {


    void draw(Canvas canvas, int sequence, Rect rect, TableConfig config);

}
