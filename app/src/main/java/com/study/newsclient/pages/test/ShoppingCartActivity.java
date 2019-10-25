package com.study.newsclient.pages.test;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.study.newsclient.R;
import com.study.newsclient.adapter.shopping.ShoppingCartAdapter;
import com.study.newsclient.base.BaseActivity;
import com.study.newsclient.bean.ShoppingCart;
import com.yuxuan.common.adapter.recycler.helper.FullyLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Time: 2019-08-29
 * Author:wyy
 * Description:
 */
public class ShoppingCartActivity extends BaseActivity {


    private RecyclerView mRecyclerView;
    private ArrayList<ShoppingCart> shoppingCarts=new ArrayList<>();
    private ShoppingCartAdapter shoppingCartAdapter;
    private FullyLinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_shopping_cart);
    }
    /**
     * 初始化标题
     */
    private void initTitleBar() {
        RelativeLayout bar_rl_left = (RelativeLayout) findViewById(R.id.bar_rl_left);
        bar_rl_left.setVisibility(View.GONE);
        bar_rl_left.setOnClickListener(this);
        TextView bar_tv_title = (TextView) findViewById(R.id.bar_tv_title);
        bar_tv_title.setText("采购单");

    }
    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        initTitleBar();
        shoppingCarts.add(new ShoppingCart(3));
        shoppingCarts.add(new ShoppingCart(3));
        shoppingCarts.add(new ShoppingCart(3));
        shoppingCarts.add(new ShoppingCart(3));
        shoppingCarts.add(new ShoppingCart(3));
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        linearLayoutManager = new FullyLinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //优化嵌套卡顿
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setNestedScrollingEnabled(false);
//        mRecyclerView.setItemViewCacheSize(600);
//        RecyclerView.RecycledViewPool recycledViewPool = new
//                RecyclerView.RecycledViewPool();
//        mRecyclerView.setRecycledViewPool(recycledViewPool);
        shoppingCartAdapter = new ShoppingCartAdapter(mContext,shoppingCarts);
        mRecyclerView.setAdapter(shoppingCartAdapter);
    }

    @Override
    protected void bindEvent() {

    }

    @Override
    public void processClick(View paramView) {

    }
}
