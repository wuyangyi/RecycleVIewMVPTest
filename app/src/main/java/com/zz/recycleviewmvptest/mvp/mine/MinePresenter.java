package com.zz.recycleviewmvptest.mvp.mine;

import com.zz.recycleviewmvptest.mvp.BasePresenter;
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

    @Override
    public void loadData() {

    }
}
