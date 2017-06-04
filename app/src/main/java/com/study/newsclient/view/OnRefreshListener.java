package com.study.newsclient.view;

/**
 * Created by wyy on 2016/3/3.
 */
public interface OnRefreshListener {
    /**
     * 下拉刷新
     */
    void onDownPullRefresh();

    /**
     * 上拉加载更多
     */
    void onLoadingMore();
}
