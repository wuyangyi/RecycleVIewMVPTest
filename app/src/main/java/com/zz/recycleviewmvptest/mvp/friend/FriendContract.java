package com.zz.recycleviewmvptest.mvp.friend;

import com.zz.recycleviewmvptest.base.BaseListView;
import com.zz.recycleviewmvptest.base.IBaseListPresenter;
import com.zz.recycleviewmvptest.bean.FlListBean;

public interface FriendContract {
    interface View extends BaseListView<FriendContract.Presenter, FlListBean.ResultsListBean> {

    }
    interface Presenter extends IBaseListPresenter {

    }
}
