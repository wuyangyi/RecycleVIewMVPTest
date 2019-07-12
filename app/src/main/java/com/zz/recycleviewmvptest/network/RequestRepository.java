package com.zz.recycleviewmvptest.network;

import com.zz.recycleviewmvptest.bean.FlListBean;
import com.zz.recycleviewmvptest.bean.PageListListBean;
import retrofit2.Call;

public class RequestRepository {
    RequestClient mRequestClient;

    public RequestRepository() {
        mRequestClient = NetWorkManager.getRequest();
    }


    public Call<PageListListBean> getPageData(int limit, int pageNumber) {
        return mRequestClient.getPageData(limit, pageNumber);
    }

    public Call<FlListBean> getFlData(int limit, int pageNumber) {
        return mRequestClient.getFlData(limit, pageNumber);
    }

}
