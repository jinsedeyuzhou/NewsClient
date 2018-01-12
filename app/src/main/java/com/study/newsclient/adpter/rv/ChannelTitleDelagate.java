package com.study.newsclient.adpter.rv;

import com.study.newsclient.R;
import com.study.newsclient.bean.Channel;
import com.yuxuan.common.adapter.recycler.absrecyclerview.ItemViewDelegate;
import com.yuxuan.common.adapter.recycler.absrecyclerview.ViewHolder;

/**
 * Created by Administrator on 2018/1/12.
 */

public class ChannelTitleDelagate implements ItemViewDelegate<Channel> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_channel_title;
    }

    @Override
    public boolean isForViewType(Channel item, int position) {
        return false;
    }

    @Override
    public void convert(ViewHolder holder, Channel channel, int position) {

    }
}
