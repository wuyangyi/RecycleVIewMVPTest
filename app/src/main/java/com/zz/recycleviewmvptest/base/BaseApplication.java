package com.zz.recycleviewmvptest.base;

import android.app.Application;
import android.content.Context;

import com.zz.recycleviewmvptest.network.NetWorkManager;

public class BaseApplication extends Application {
    private static BaseApplication mApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        NetWorkManager.getInstance().init();
        mApplication = this;
    }

    public static Context getContext() {
        return mApplication;
    }
}
