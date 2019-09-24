package com.zz.recycleviewmvptest.base;

import com.zz.recycleviewmvptest.bean.MyInfoBean;
import com.zz.recycleviewmvptest.bean.local.MyInfoBeanDaoImpl;

import java.util.List;

public abstract class BasePresenter<V extends BaseView> {
    protected V mRootView;
    protected MyInfoBeanDaoImpl mMyInfoBeanDaoImpl;
    private MyInfoBean mMyInfoBean;
    public BasePresenter(V rootView){
        this.mRootView = rootView;
        mMyInfoBeanDaoImpl = new MyInfoBeanDaoImpl();
    }

    public MyInfoBean getUserInfo() {
        if (isLogin()) {
            return mMyInfoBean;
        }
        return null;
    }

    public boolean isLogin() {
        List<MyInfoBean> users = mMyInfoBeanDaoImpl.getAllList();
        if (users != null && users.size() > 0 && users.get(0).isLogin()) {
            if (mMyInfoBean == null) {
                mMyInfoBean = users.get(0);
            }
            return true;
        }
        return false;
    }

    protected void setUserInfo(MyInfoBean myInfoBean) {
        mMyInfoBean = myInfoBean;
        mMyInfoBeanDaoImpl.clearTable();
        mMyInfoBeanDaoImpl.insertOrReplace(myInfoBean);
    }
}
