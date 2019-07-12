package com.zz.recycleviewmvptest.mvp.mine;

import com.zz.recycleviewmvptest.base.BaseView;
import com.zz.recycleviewmvptest.base.IBasePresenter;

public class MineContract {
    interface View extends BaseView<Presenter> {
//        void loadDataSuccess();
    }

    interface Presenter extends IBasePresenter {

    }
}
