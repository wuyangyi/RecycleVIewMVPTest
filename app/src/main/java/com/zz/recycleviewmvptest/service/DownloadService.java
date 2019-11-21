package com.zz.recycleviewmvptest.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.zz.recycleviewmvptest.network.ApiConfig;
import com.zz.recycleviewmvptest.network.download.DownInfo;
import com.zz.recycleviewmvptest.network.download.HttpDownManager;
import com.zz.recycleviewmvptest.network.download.listener.HttpProgressOnNextListener;
import com.zz.recycleviewmvptest.notification.NotificationBarUtil;
import com.zz.recycleviewmvptest.notification.NotificationHelper;
import com.zz.recycleviewmvptest.util.ApkUtils;
import com.zz.recycleviewmvptest.util.StorageUtils;

import java.io.File;

/**
 * author: wuyangyi
 * date: 2019-11-21
 */
public class DownloadService extends Service {
    private DownInfo downInfo;
    private NotificationHelper notificationHelper;
    private int oldProgress = 0; //上次进度条
    Intent intentUp = new Intent(DownloadService.DOWNLOAD_UP);

    public final static String DOWNLOAD_START = "download_start"; //开始下载
    public final static String DOWNLOAD_GO_ON_START = "download_go_on_start"; //继续下载
    public final static String DOWNLOAD_STOP = "dounload_stop"; //暂停下载
    public final static String DOWNLOAD_UP = "dounload_up"; //更新进度

    @Override
    public void onCreate() {
        super.onCreate();
        downInfo = new DownInfo();
        notificationHelper = new NotificationHelper(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        assert intent != null;
        String urlStr = intent.getStringExtra(ApiConfig.DOWNLOAD_URL);
        downInfo.setUrl(urlStr);
        File dir = StorageUtils.getExternalCacheCustomDirectory(this);
//        File dir = StorageUtils.getExternalCacheDirectory(this);
//        File dir = StorageUtils.getCacheDirectory(this);
        String apkName = urlStr.substring(urlStr.lastIndexOf("/") + 1, urlStr.length());
        File apkFile = new File(dir, apkName);
        downInfo.setSavePath(apkFile.getAbsolutePath());
        if (intent.getAction() == null) {
            downLoadFile();
        } else {
            if (intent.getAction().equals(DOWNLOAD_START)) {
                downLoadFile();
            } else if (intent.getAction().equals(DOWNLOAD_STOP)) { //停止下载
                stopDown();
                intentUp.putExtra("progress", -1);
                sendBroadcast(intentUp);
                Log.d("点击了", "暂停");
            } else if (intent.getAction().equals(DOWNLOAD_GO_ON_START)){
                goOnStart();
                intentUp.putExtra("progress", oldProgress);
                sendBroadcast(intentUp);
                Log.d("点击了", "继续");
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void downLoadFile() {
        HttpDownManager.getInstance().startDown(downInfo, new HttpProgressOnNextListener<DownInfo>() {
            @Override
            public void onNext(DownInfo downInfo) {
                //收起通知栏
                NotificationBarUtil.setNotificationBarVisibility(DownloadService.this, false);
                //安装
                ApkUtils.installAPk(DownloadService.this, new File(downInfo.getSavePath()));
            }

            @Override
            public void onComplete() {
                notificationHelper.cancel();
                stopSelf();
            }

            @Override
            public void updateProgress(long readLength, long countLength) {
                int progress = (int) (readLength * 100 / countLength);
                // 如果进度与之前进度相等，则不更新，如果更新太频繁，否则会造成界面卡顿
                if (progress != oldProgress) {
                    notificationHelper.updateProgress(progress);
                    intentUp.putExtra("progress", progress);
                    sendBroadcast(intentUp);
                }
                oldProgress = progress;
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(DownloadService.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                intentUp.putExtra("progress", -1);
                sendBroadcast(intentUp);
                notificationHelper.stopProgress(oldProgress);
            }
        });
    }

    //暂停下载
    private void stopDown() {
        HttpDownManager.getInstance().pause(downInfo);
    }

    //继续下载
    private void goOnStart() {
        HttpDownManager.getInstance().continueDownload(downInfo);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
