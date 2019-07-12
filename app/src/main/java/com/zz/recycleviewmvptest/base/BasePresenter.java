package com.zz.recycleviewmvptest.base;

public abstract class BasePresenter<V extends BaseView> {
    protected V mRootView;
    public BasePresenter(V rootView){
        this.mRootView = rootView;
    }
}
