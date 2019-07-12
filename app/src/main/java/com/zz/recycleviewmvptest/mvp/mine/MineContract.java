package com.zz.recycleviewmvptest.mvp.mine;

import com.zz.recycleviewmvptest.mvp.BaseView;
import com.zz.recycleviewmvptest.mvp.IBasePresenter;

public class MineContract {
    interface View extends BaseView<Presenter> {
//        void loadDataSuccess();
    }

    interface Presenter extends IBasePresenter {

    }
}
