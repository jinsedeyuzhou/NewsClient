package com.study.newsclient.adapter.shopping;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.study.newsclient.R;
import com.study.newsclient.bean.ShoppingCart;
import com.yuxuan.common.adapter.recycler.absrecyclerview.ItemViewDelegate;
import com.yuxuan.common.adapter.recycler.absrecyclerview.ViewHolder;
import com.yuxuan.common.adapter.recycler.divider.DividerItemDecoration;

/**
 * Time: 2019-08-29
 * Author:wyy
 * Description:
 */
public class ShoppingCartItemViewDelegate implements ItemViewDelegate<ShoppingCart> {

    private Context mContext;
    public ShoppingCartItemViewDelegate(Context mContext) {
        this.mContext=mContext;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_shopping_cart;
    }

    @Override
    public boolean isForViewType(ShoppingCart item, int position) {
        return item.getType()==ShoppingCart.SHOPPING_CART_MORE;
    }

    @Override
    public void convert(ViewHolder holder, ShoppingCart shoppingCart, int position) {
        RecyclerView childRecyclerView = holder.getView(R.id.childRecyclerView);
//        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false){
//            @Override
//            public boolean canScrollVertically() {
//                return false;
//            }
//        };
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mContext);
        childRecyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL_LIST));
        childRecyclerView.setLayoutManager(linearLayoutManager);
        ShoppingCartChildAdapter shoppingCartChildAdapter=new ShoppingCartChildAdapter(mContext,R.layout.item_child_shopping_cart,shoppingCart.getGoodsList());
        childRecyclerView.setAdapter(shoppingCartChildAdapter);


    }
}
