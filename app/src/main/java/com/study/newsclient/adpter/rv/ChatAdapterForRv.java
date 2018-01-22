package com.study.newsclient.adpter.rv;

import android.content.Context;


import com.study.newsclient.bean.ChatMessage;
import com.yuxuan.common.adapter.recycler.absrecyclerview.MultiItemTypeAdapter;

import java.util.List;

/**
 * Created by zhy on 15/9/4.
 */
public class ChatAdapterForRv extends MultiItemTypeAdapter<ChatMessage>
{
    public ChatAdapterForRv(Context context, List<ChatMessage> datas)
    {
        super(context, datas);
        addItemViewDelegate(new MsgSendItemDelagate());
        addItemViewDelegate(new MsgComingItemDelagate());
    }


}
