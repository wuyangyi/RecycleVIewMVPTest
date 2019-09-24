package com.zz.recycleviewmvptest.mvp.mine;

import com.zz.recycleviewmvptest.base.BaseListPresenter;
import com.zz.recycleviewmvptest.base.BasePresenter;
import com.zz.recycleviewmvptest.bean.UserInfoBean;
import com.zz.recycleviewmvptest.bean.local.MyInfoBeanDaoImpl;
import com.zz.recycleviewmvptest.bean.local.UserInfoBeanDaoImpl;
import com.zz.recycleviewmvptest.network.RequestRepository;

/**
 * 我的界面
 */
public class MinePresenter extends BasePresenter<MineContract.View> implements MineContract.Presenter {
    private MyInfoBeanDaoImpl mMyInfoBeanDaoImpl;

    public MinePresenter(MineContract.View rootView) {
        super(rootView);
        mMyInfoBeanDaoImpl = new MyInfoBeanDaoImpl();
    }


    @Override
    public void sendUserInfo() {
        mRootView.getUserInfo(getUserInfo());
    }
}
