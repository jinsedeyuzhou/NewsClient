package com.study.newsclient.adapter.rv;


import android.widget.ImageView;

import com.study.newsclient.R;
import com.study.newsclient.bean.ChatMessage;
import com.yuxuan.common.adapter.recycler.absrecyclerview.ItemViewDelegate;
import com.yuxuan.common.adapter.recycler.absrecyclerview.ViewHolder;
import com.yuxuan.common.widget.imageloader.ILoader;
import com.yuxuan.common.widget.imageloader.LoaderManager;

/**
 * Created by zhy on 16/6/22.
 */
public class MsgComingItemDelagate implements ItemViewDelegate<ChatMessage>
{

    @Override
    public int getItemViewLayoutId()
    {
        return R.layout.main_chat_from_msg;
    }

    @Override
    public boolean isForViewType(ChatMessage item, int position)
    {
        return item.isComMeg();
    }

    @Override
    public void convert(ViewHolder holder, ChatMessage chatMessage, int position)
    {
//        holder.setText(R.id.chat_from_content, chatMessage.getContent());
        holder.setText(R.id.chat_from_name, chatMessage.getName());
        holder.setImageResource(R.id.chat_from_icon, chatMessage.getIcon());
        if (chatMessage.getType()==2) {
            holder.setVisible(R.id.chat_from_content,false);
            holder.setVisible(R.id.show_face_img,true);
            LoaderManager.getLoader().loadNet((ImageView) holder.getView(R.id.show_face_img), chatMessage.getContent()
                    , new ILoader.Options(R.drawable.ic_loading, R.drawable.ic_loading));
        }else
        {
            holder.setVisible(R.id.chat_from_content,true);
            holder.setVisible(R.id.show_face_img,false);
            holder.setText(R.id.chat_from_content, chatMessage.getContent());
        }
    }
}
