package com.study.newsclient.adapter.shopping;

import android.content.Context;

import com.study.newsclient.bean.ShoppingCart;
import com.yuxuan.common.adapter.recycler.absrecyclerview.CommonAdapter;
import com.yuxuan.common.adapter.recycler.absrecyclerview.ViewHolder;

import java.util.List;

/**
 * Time: 2019-08-29
 * Author:wyy
 * Description:
 */
public class ShoppingCartChildAdapter extends CommonAdapter<ShoppingCart> {
    public ShoppingCartChildAdapter(Context context, int layoutId, List<ShoppingCart> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ShoppingCart shoppingCart, int position) {

    }
}
