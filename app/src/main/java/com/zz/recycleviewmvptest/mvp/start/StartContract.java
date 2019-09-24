package com.zz.recycleviewmvptest.mvp.start;

import com.zz.recycleviewmvptest.base.BaseView;
import com.zz.recycleviewmvptest.base.IBasePresenter;

/**
 * author: wuyangyi
 * date: 2019-09-24
 */
public interface StartContract {

    interface View extends BaseView<Presenter> {
        void setJumpText(String text);

        void startToHome();
    }
    interface Presenter extends IBasePresenter {
        void startTime();

        void stopTime();
    }
}
