package com.study.newsclient.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Time: 2019-08-29
 * Author:wyy
 * Description:
 */
public class ShoppingCart implements Serializable {

    public static final int SHOPPING_CART_PROMOTION=1;
    public static final int SHOPPING_CART_SINGLE=2;
    public static final int SHOPPING_CART_MORE=3;

    public ShoppingCart(int type) {
        this.type = type;
    }

    private List<ShoppingCart> goodsList;

    public List<ShoppingCart> getGoodsList() {
        goodsList=new ArrayList<>();
        goodsList.add(new ShoppingCart(1));
        goodsList.add(new ShoppingCart(1));
        goodsList.add(new ShoppingCart(1));
        goodsList.add(new ShoppingCart(1));
        goodsList.add(new ShoppingCart(1));
        return goodsList;
    }

    public void setGoodsList(List<ShoppingCart> goodsList) {
        this.goodsList = goodsList;
    }

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


}
