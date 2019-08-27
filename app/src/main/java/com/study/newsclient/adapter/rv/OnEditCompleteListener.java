package com.study.newsclient.adapter.rv;

import com.study.newsclient.bean.Channel;

import java.util.ArrayList;

/**
 * Created by Chu on 2017/7/11.
 */

public interface OnEditCompleteListener {
    void onEditFinish(ArrayList<Channel> selectedLabels, ArrayList<Channel> unselectedLabel, ArrayList<Channel> alwaySelectedLabels);
}
