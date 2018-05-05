package com.study.newsclient.adapter.rv;

import android.content.Context;

import com.study.newsclient.bean.Channel;
import com.yuxuan.common.adapter.recycler.absrecyclerview.MultiItemTypeAdapter;

import java.util.List;

/**
 * Created by Administrator on 2018/1/12.
 */

public class ChannelAdapter extends MultiItemTypeAdapter<Channel> {

    public ChannelAdapter(Context context, List<Channel> datas) {
        super(context, datas);
        addItemViewDelegate(new ChannelTitleDelagate());
        addItemViewDelegate(new ChannelItemDelagate());
    }
}
