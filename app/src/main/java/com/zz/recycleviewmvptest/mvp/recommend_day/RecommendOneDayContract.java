package com.zz.recycleviewmvptest.mvp.recommend_day;

import com.zz.recycleviewmvptest.base.BaseView;
import com.zz.recycleviewmvptest.base.IBasePresenter;

import org.json.JSONObject;

/**
 * author: wuyangyi
 * date: 2019-09-18
 * 每日推荐
 */
public interface RecommendOneDayContract {
    interface View extends BaseView<Presenter> {
        void loadDataResult(boolean isSuccess, JSONObject jsonObject);
    }

    interface Presenter extends IBasePresenter {
        void loadData();
    }
}
