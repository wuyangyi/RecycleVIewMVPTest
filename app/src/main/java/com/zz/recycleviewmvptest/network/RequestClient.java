package com.zz.recycleviewmvptest.network;

import com.zz.recycleviewmvptest.bean.FlListBean;
import com.zz.recycleviewmvptest.bean.PageListListBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 请求网络接口
 */
public interface RequestClient {

    @GET(ApiConfig.APP_PATH_LIST_PAGE)
    Call<PageListListBean> getPageData(@Path ("limit")int limit, @Path("pageNumber") int pageNumber);

    @GET(ApiConfig.APP_PATH_LIST_FL)
    Call<FlListBean> getFlData(@Path ("limit")int limit, @Path("pageNumber") int pageNumber);
}
