package com.zz.recycleviewmvptest.mvp.page_list;

import com.zz.recycleviewmvptest.bean.FlBean;
import com.zz.recycleviewmvptest.bean.PageListBean;
import com.zz.recycleviewmvptest.mvp.BasePresenter;
import com.zz.recycleviewmvptest.network.RequestRepository;

import retrofit2.Callback;
import retrofit2.Response;



public class PageListPresenter extends BasePresenter<PageListContract.View> implements PageListContract.Presenter {
    private RequestRepository mRequestRepository;

    public PageListPresenter(PageListContract.View rootView) {
        super(rootView);
        mRequestRepository = new RequestRepository();

    }
    @Override
    public void loadData() {
        mRequestRepository.getFlData(10, 1).enqueue(new Callback<FlBean>() {
            @Override
            public void onResponse(retrofit2.Call<FlBean> call, Response<FlBean> response) {
                mRootView.sendDataSuccess(response.body().getResults());
            }

            @Override
            public void onFailure(retrofit2.Call<FlBean> call, Throwable t) {
                mRootView.failDeal();
            }
        });
    }
}
