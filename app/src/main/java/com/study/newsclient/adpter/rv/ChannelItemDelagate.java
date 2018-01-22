package com.study.newsclient.adpter.rv;

import com.study.newsclient.R;
import com.study.newsclient.bean.Channel;
import com.yuxuan.common.adapter.recycler.absrecyclerview.ItemViewDelegate;
import com.yuxuan.common.adapter.recycler.absrecyclerview.ViewHolder;

/**
 * Created by Administrator on 2018/1/12.
 */

public class ChannelItemDelagate implements ItemViewDelegate<Channel> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_channel;
    }

    @Override
    public boolean isForViewType(Channel item, int position) {
        return item.getItemType()==Channel.TYPE_MY_CHANNEL||item.getItemType()==Channel.TYPE_OTHER_CHANNEL;
    }

    @Override
    public void convert(ViewHolder holder, Channel channel, int position) {
        holder.setText(R.id.tvChannel,channel.getCname());


    }
}
