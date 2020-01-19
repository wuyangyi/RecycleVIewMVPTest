package com.zz.recycleviewmvptest.base;

import java.util.List;

public interface BaseListView<P, B> extends BaseView<P> {
    /**
     * 加载成功
     * @param data
     * @param isLoadMore
     */
    void onNetSuccess(List<B> data, boolean isLoadMore);

    /**
     * 加载失败
     */
    void onNetFailing();

    /**
     * 手动刷新
     */
    void startRefresh();
}
