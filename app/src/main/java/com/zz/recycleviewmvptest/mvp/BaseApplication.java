package com.zz.recycleviewmvptest.mvp;

import android.app.Application;

import com.zz.recycleviewmvptest.network.NetWorkManager;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NetWorkManager.getInstance().init();
    }
}
