package com.zz.recycleviewmvptest.mvp.password;

import com.zz.recycleviewmvptest.base.BaseView;
import com.zz.recycleviewmvptest.base.IBasePresenter;

/**
 * author: wuyangyi
 * date: 2019-09-24
 */
public interface UpPasswordContract {
    interface View extends BaseView<Presenter> {
        void upPwdSuccess();
    }

    interface Presenter extends IBasePresenter {
        void setPwd(String phone, String pwd);

        void upPwd(String phone, String pwd, String oldPwd);
    }
}
