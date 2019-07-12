package com.zz.recycleviewmvptest.mvp.page_list;

import com.zz.recycleviewmvptest.bean.FlBean;
import com.zz.recycleviewmvptest.bean.PageListBean;
import com.zz.recycleviewmvptest.mvp.BasePresenter;
import com.zz.recycleviewmvptest.mvp.BaseView;
import com.zz.recycleviewmvptest.mvp.IBasePresenter;

import java.util.List;

public interface PageListContract {
    interface View extends BaseView<Presenter> {
        void sendDataSuccess(List<FlBean.ResultsBean> data);

        /**
         * 异常处理
         */
        void failDeal();
    }
    interface Presenter extends IBasePresenter {

    }
}
