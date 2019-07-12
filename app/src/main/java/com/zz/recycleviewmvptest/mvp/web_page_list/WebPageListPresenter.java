package com.zz.recycleviewmvptest.mvp.web_page_list;

import com.zz.recycleviewmvptest.base.BaseListFragment;
import com.zz.recycleviewmvptest.base.BaseListPresenter;
import com.zz.recycleviewmvptest.bean.PageListListBean;
import com.zz.recycleviewmvptest.network.RequestRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebPageListPresenter extends BaseListPresenter<WebPageListContract.View> implements WebPageListContract.Presenter {
    private RequestRepository mRequestRepository;
    public WebPageListPresenter(WebPageListContract.View rootView) {
        super(rootView);
        mRequestRepository = new RequestRepository();
    }

    @Override
    public void requestNetData(int maxId, final boolean isLoadMore, int page) {
        mRequestRepository.getPageData(BaseListFragment.PAGE_LIST_NUMBER, page).enqueue(new Callback<PageListListBean>() {
            @Override
            public void onResponse(Call<PageListListBean> call, Response<PageListListBean> response) {
                mRootView.onNetSuccess(response.body().getResults(), isLoadMore);
            }

            @Override
            public void onFailure(Call<PageListListBean> call, Throwable t) {
                mRootView.onNetFailing();
            }
        });
    }
}
