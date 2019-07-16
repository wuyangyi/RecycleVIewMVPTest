package com.zz.recycleviewmvptest.mvp.mine.add_user;

import com.zz.recycleviewmvptest.base.BasePresenter;
import com.zz.recycleviewmvptest.base.BaseView;
import com.zz.recycleviewmvptest.base.IBasePresenter;
import com.zz.recycleviewmvptest.bean.UserInfoBean;

public interface AddUserContract {
    interface View extends BaseView<Presenter> {
        void saveSuccess();
    }

    interface Presenter extends IBasePresenter {
        void saveUser(UserInfoBean userInfoBean);
    }
}
