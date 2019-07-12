package com.zz.recycleviewmvptest.network;

import com.zz.recycleviewmvptest.bean.FlBean;
import com.zz.recycleviewmvptest.bean.PageListBean;
import retrofit2.Call;

public class RequestRepository {
    RequestClient mRequestClient;

    public RequestRepository() {
        mRequestClient = NetWorkManager.getRequest();
    }


    public Call<PageListBean> getPageData(int limit, int pageNumber) {
        return mRequestClient.getPageData(limit, pageNumber);
    }

    public Call<FlBean> getFlData(int limit, int pageNumber) {
        return mRequestClient.getFlData(limit, pageNumber);
    }

}
