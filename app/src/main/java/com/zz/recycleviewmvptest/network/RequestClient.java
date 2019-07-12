package com.zz.recycleviewmvptest.network;

import com.zz.recycleviewmvptest.bean.FlBean;
import com.zz.recycleviewmvptest.bean.PageListBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 请求网络接口
 */
public interface RequestClient {

    @GET(ApiConfig.APP_PATH_LIST_PAGE)
    Call<PageListBean> getPageData(@Path ("limit")int limit, @Path("pageNumber") int pageNumber);

    @GET(ApiConfig.APP_PATH_LIST_FL)
    Call<FlBean> getFlData(@Path ("limit")int limit, @Path("pageNumber") int pageNumber);
}
