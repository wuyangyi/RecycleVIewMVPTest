package com.zz.recycleviewmvptest.network;

import com.zz.recycleviewmvptest.bean.Ask;
import com.zz.recycleviewmvptest.bean.FlListBean;
import com.zz.recycleviewmvptest.bean.PageListListBean;
import com.zz.recycleviewmvptest.bean.TLInputBean;
import com.zz.recycleviewmvptest.bean.TLOutputBean;
import com.zz.recycleviewmvptest.bean.Take;

import retrofit2.Call;

public class RequestRepository {
    RequestClient mRequestClient;
    RequestClient mRequestClientTl; //图灵

    public RequestRepository() {
        mRequestClient = NetWorkManager.getRequest();
        mRequestClientTl = NetWorkManager.getRequestTL();
    }


    public Call<PageListListBean> getPageData(int limit, int pageNumber) {
        return mRequestClient.getPageData(limit, pageNumber);
    }

    public Call<FlListBean> getFlData(int limit, int pageNumber) {
        return mRequestClient.getFlData(limit, pageNumber);
    }

    /**
     * 图灵机器人聊天
     * @return
     */
    public Call<Take> sendMessage(Ask data) {
        return mRequestClientTl.sendMessage(data);
    }

}
