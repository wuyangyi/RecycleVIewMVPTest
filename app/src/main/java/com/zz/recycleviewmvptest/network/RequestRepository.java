package com.zz.recycleviewmvptest.network;

import com.zz.recycleviewmvptest.bean.AppVersionBean;
import com.zz.recycleviewmvptest.bean.Ask;
import com.zz.recycleviewmvptest.bean.FlListBean;
import com.zz.recycleviewmvptest.bean.PageListListBean;
import com.zz.recycleviewmvptest.bean.TLInputBean;
import com.zz.recycleviewmvptest.bean.TLOutputBean;
import com.zz.recycleviewmvptest.bean.Take;

import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class RequestRepository {
    RequestClient mRequestClient;
    RequestClient mRequestClientTl; //图灵
    RequestClient mRequestClientHb; //github

    public RequestRepository() {
        mRequestClient = NetWorkManager.getRequest();
        mRequestClientTl = NetWorkManager.getRequestTL();
        mRequestClientHb = NetWorkManager.getRequestHB();
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

    /**
     * 每日推荐
     * @return
     */
    public Call<ResponseBody> getRecommend() {
        return mRequestClient.getRecommend();
    }

    /**
     * app版本信息
     * @return
     */
    public Observable<Response<AppVersionBean>> getAppVersion() {
        return mRequestClientHb.getUpAppInfo()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
