package com.zz.recycleviewmvptest.mvp.base_adapter;

public interface ItemViewDelegate<T> {

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(ViewHolder holder, T t, T lastT, int position,int itemCounts);

}
