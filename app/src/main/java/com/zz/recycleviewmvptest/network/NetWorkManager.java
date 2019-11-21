package com.zz.recycleviewmvptest.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetWorkManager {

    private static NetWorkManager mInstance;
    private static Retrofit retrofit;
    private static Retrofit mTlRetrofit;
    private static Retrofit mGHRetrofit; //github
    private static volatile RequestClient request = null;
    private static volatile RequestClient requestTl = null;
    private static volatile RequestClient requestGb = null;

    public static NetWorkManager getInstance() {
        if (mInstance == null) {
            synchronized (NetWorkManager.class) {
                if (mInstance == null) {
                    mInstance = new NetWorkManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化必要对象和参数
     */
    public void init() {
        // 初始化okhttp
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        OkHttpClient clients = new OkHttpClient.Builder()
                .build();

        // 初始化Retrofit
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(ApiConfig.APP_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mTlRetrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(ApiConfig.APP_TL_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mGHRetrofit = new Retrofit.Builder()
                .client(clients)
                .baseUrl(ApiConfig.APP_GITHUB_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RequestClient getRequest() {
        if (request == null) {
            synchronized (RequestClient.class) {
                request = retrofit.create(RequestClient.class);
            }
        }
        return request;
    }

    public static RequestClient getRequestTL() {
        if (requestTl == null) {
            synchronized (RequestClient.class) {
                requestTl = mTlRetrofit.create(RequestClient.class);
            }
        }
        return requestTl;
    }

    public static RequestClient getRequestHB() {
        if (requestGb == null) {
            synchronized (RequestClient.class) {
                requestGb = mGHRetrofit.create(RequestClient.class);
            }
        }
        return requestGb;
    }
}
