package com.study.newsclient.adapter.shopping;

import android.content.Context;

import com.study.newsclient.bean.ShoppingCart;
import com.yuxuan.common.adapter.recycler.absrecyclerview.MultiItemTypeAdapter;

import java.util.List;

/**
 * Time: 2019-08-29
 * Author:wyy
 * Description:
 */
public class ShoppingCartAdapter extends MultiItemTypeAdapter<ShoppingCart> {

    public ShoppingCartAdapter(Context context, List<ShoppingCart> datas) {
        super(context, datas);
        addItemViewDelegate(new ShoppingCartItemViewDelegate(context));
    }
}
