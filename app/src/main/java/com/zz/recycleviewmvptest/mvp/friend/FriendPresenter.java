package com.zz.recycleviewmvptest.mvp.friend;

import com.zz.recycleviewmvptest.base.BaseListFragment;
import com.zz.recycleviewmvptest.base.BaseListPresenter;
import com.zz.recycleviewmvptest.bean.FlListBean;
import com.zz.recycleviewmvptest.network.RequestRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendPresenter extends BaseListPresenter<FriendContract.View> implements FriendContract.Presenter {
    private RequestRepository mRequestRepository;
    public FriendPresenter(FriendContract.View rootView) {
        super(rootView);
        mRequestRepository = new RequestRepository();
    }

    @Override
    public void requestNetData(int maxId, final boolean isLoadMore, int page) {
        mRequestRepository.getFlData(BaseListFragment.PAGE_LIST_NUMBER, page)
                .enqueue(new Callback<FlListBean>() {
                    @Override
                    public void onResponse(Call<FlListBean> call, Response<FlListBean> response) {
                        mRootView.onNetSuccess(response.body().getResults(), isLoadMore);
                    }

                    @Override
                    public void onFailure(Call<FlListBean> call, Throwable t) {
                        mRootView.onNetFailing();
                    }
                });
    }
}
