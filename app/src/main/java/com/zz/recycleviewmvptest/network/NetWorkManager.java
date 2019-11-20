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
                .connectTimeout(15 * 1000, TimeUnit.SECONDS)
                .readTimeout(60 * 1000, TimeUnit.MICROSECONDS)
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
        if (request == null) {
            synchronized (RequestClient.class) {
                request = mTlRetrofit.create(RequestClient.class);
            }
        }
        return request;
    }

    public static RequestClient getRequestHB() {
        if (request == null) {
            synchronized (RequestClient.class) {
                request = mGHRetrofit.create(RequestClient.class);
            }
        }
        return request;
    }
}
