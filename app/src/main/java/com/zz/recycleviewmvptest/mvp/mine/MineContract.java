package com.zz.recycleviewmvptest.mvp.mine;

import com.zz.recycleviewmvptest.base.BaseListView;
import com.zz.recycleviewmvptest.base.BasePresenter;
import com.zz.recycleviewmvptest.base.BaseView;
import com.zz.recycleviewmvptest.base.IBaseListPresenter;
import com.zz.recycleviewmvptest.base.IBasePresenter;
import com.zz.recycleviewmvptest.bean.MyInfoBean;
import com.zz.recycleviewmvptest.bean.UserInfoBean;

public class MineContract {
    interface View extends BaseView<Presenter> {
        void getUserInfo(MyInfoBean myInfoBean);
    }

    interface Presenter extends IBasePresenter {
        void sendUserInfo();
    }
}
