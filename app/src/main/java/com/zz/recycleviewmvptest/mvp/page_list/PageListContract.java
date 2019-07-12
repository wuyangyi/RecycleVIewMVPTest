package com.zz.recycleviewmvptest.mvp.page_list;

import com.zz.recycleviewmvptest.base.BaseListView;
import com.zz.recycleviewmvptest.base.IBaseListPresenter;
import com.zz.recycleviewmvptest.bean.FlListBean;
import com.zz.recycleviewmvptest.base.BaseView;
import com.zz.recycleviewmvptest.base.IBasePresenter;

import java.util.List;

public interface PageListContract {
    interface View extends BaseListView<Presenter, FlListBean.ResultsListBean> {

    }
    interface Presenter extends IBaseListPresenter {

    }
}
