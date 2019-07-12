package com.zz.recycleviewmvptest.base;


public interface IBaseListPresenter extends IBasePresenter {
    /**
     * 加载数据
     * @param maxId
     * @param isLoadMore
     */
    void requestNetData(int maxId, boolean isLoadMore, int page);
}
