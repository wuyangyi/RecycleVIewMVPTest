package com.zz.recycleviewmvptest.mvp.web_page_list;

import com.zz.recycleviewmvptest.base.BaseListView;
import com.zz.recycleviewmvptest.base.IBaseListPresenter;
import com.zz.recycleviewmvptest.bean.PageListListBean;

public interface WebPageListContract {
    interface View extends BaseListView<Presenter, PageListListBean.ResultsListBean> {

    }

    interface Presenter extends IBaseListPresenter {

    }
}
