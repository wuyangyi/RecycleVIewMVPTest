package com.zz.recycleviewmvptest.mvp.setting;

import com.zz.recycleviewmvptest.base.BaseView;
import com.zz.recycleviewmvptest.base.IBasePresenter;
import com.zz.recycleviewmvptest.bean.AppVersionBean;

/**
 * author: wuyangyi
 * date: 2019-09-24
 */
public interface SettingContract {
    interface View extends BaseView<Presenter> {
        void outLoginSuccess();

        void checkAppVersionSuccess(AppVersionBean data);
    }

    interface Presenter extends IBasePresenter {
        void outLogin();

        void checkAppVersion();
    }
}
