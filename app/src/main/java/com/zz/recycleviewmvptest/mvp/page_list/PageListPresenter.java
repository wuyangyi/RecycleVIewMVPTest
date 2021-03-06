package com.zz.recycleviewmvptest.mvp.page_list;

import com.zz.recycleviewmvptest.base.BaseListFragment;
import com.zz.recycleviewmvptest.base.BaseListPresenter;
import com.zz.recycleviewmvptest.bean.FlListBean;
import com.zz.recycleviewmvptest.base.BasePresenter;
import com.zz.recycleviewmvptest.network.RequestRepository;

import retrofit2.Callback;
import retrofit2.Response;



public class PageListPresenter extends BaseListPresenter<PageListContract.View> implements PageListContract.Presenter {
    private RequestRepository mRequestRepository;

    public PageListPresenter(PageListContract.View rootView) {
        super(rootView);
        mRequestRepository = new RequestRepository();

    }

    @Override
    public void requestNetData(int maxId, final boolean isLoadMore, int page) {
        mRequestRepository.getFlData(BaseListFragment.PAGE_LIST_NUMBER, page).enqueue(new Callback<FlListBean>() {
            @Override
            public void onResponse(retrofit2.Call<FlListBean> call, Response<FlListBean> response) {
                mRootView.onNetSuccess(response.body().getResults(), isLoadMore);
            }

            @Override
            public void onFailure(retrofit2.Call<FlListBean> call, Throwable t) {
                mRootView.onNetFailing();
            }
        });
    }
}
