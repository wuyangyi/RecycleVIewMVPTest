package com.zz.recycleviewmvptest.mvp.mine;

import com.zz.recycleviewmvptest.base.BasePresenter;
import com.zz.recycleviewmvptest.network.RequestRepository;

/**
 * 我的界面
 */
public class MinePresenter extends BasePresenter<MineContract.View> implements MineContract.Presenter {
    private RequestRepository mRequestRepository;
    public MinePresenter(MineContract.View rootView) {
        super(rootView);
        mRequestRepository = new RequestRepository();
    }

}
