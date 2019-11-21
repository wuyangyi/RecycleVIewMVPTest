package com.zz.recycleviewmvptest.mvp.setting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zz.recycleviewmvptest.base.BaseActivity;
import com.zz.recycleviewmvptest.service.DownloadService;

/**
 * author: wuyangyi
 * date: 2019-09-24
 * 设置
 */
public class SettingActivity extends BaseActivity<SettingPresenter, SettingFragment> {
    @Override
    protected SettingFragment getFragment() {
        return new SettingFragment();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadService.DOWNLOAD_UP);
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    /**
     * 广播接收器
     */
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DownloadService.DOWNLOAD_UP)) {
                int progress = intent.getIntExtra("progress", 0);
                mContanierFragment.upProgress(progress);
            }
        }
    };
}
