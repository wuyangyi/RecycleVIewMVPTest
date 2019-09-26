package com.zz.recycleviewmvptest.base;

import com.zz.recycleviewmvptest.bean.MyInfoBean;

/**
 * List加载数据的Presenter封装
 * @param <V>
 */
public abstract class BaseListPresenter<V extends BaseListView> extends BasePresenter<V> implements IBaseListPresenter {

    public BaseListPresenter(V rootView) {
        super(rootView);
    }


}
