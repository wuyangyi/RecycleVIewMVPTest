package com.zz.recycleviewmvptest.base;

import android.app.Application;
import android.content.Context;

import com.hjq.toast.ToastUtils;
import com.mob.MobSDK;
import com.zz.recycleviewmvptest.network.NetWorkManager;
import com.zz.recycleviewmvptest.widget.toast.ToastSystemStyle;

public class BaseApplication extends Application {
    private static BaseApplication mApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        NetWorkManager.getInstance().init();
        mApplication = this;
        ToastUtils.init(this, new ToastSystemStyle());
        MobSDK.init(this);
    }

    public static Context getContext() {
        return mApplication;
    }
}
