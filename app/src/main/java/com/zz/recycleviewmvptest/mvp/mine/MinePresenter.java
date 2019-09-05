package com.zz.recycleviewmvptest.mvp.mine;

import com.zz.recycleviewmvptest.base.BaseListPresenter;
import com.zz.recycleviewmvptest.base.BasePresenter;
import com.zz.recycleviewmvptest.bean.local.UserInfoBeanDaoImpl;
import com.zz.recycleviewmvptest.network.RequestRepository;

/**
 * 我的界面
 */
public class MinePresenter extends BaseListPresenter<MineContract.View> implements MineContract.Presenter {
    private RequestRepository mRequestRepository;
    private UserInfoBeanDaoImpl mUserInfoBeanDaoImpl;
    public MinePresenter(MineContract.View rootView) {
        super(rootView);
        mRequestRepository = new RequestRepository();
        mUserInfoBeanDaoImpl = new UserInfoBeanDaoImpl();
    }

    @Override
    public void requestNetData(int maxId, boolean isLoadMore, int page) {
        mRootView.onNetSuccess(mUserInfoBeanDaoImpl.getAllList(), false);
    }
}
