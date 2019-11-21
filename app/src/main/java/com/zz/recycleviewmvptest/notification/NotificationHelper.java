package com.zz.recycleviewmvptest.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.network.ApiConfig;
import com.zz.recycleviewmvptest.service.DownloadService;

/**
 * author: wuyangyi
 * date: 2019-11-21
 */
public class NotificationHelper {

    private NotificationManager manager;

    private Context mContext;

    private static String CHANNEL_ID = "xiaoxiong_app_update";

    private static final int NOTIFICATION_ID = 0;

    public NotificationHelper(Context context) {
        this.mContext = context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, "应用更新", NotificationManager.IMPORTANCE_NONE);
            mChannel.setDescription("应用有新版本");
            mChannel.enableLights(true); //是否在桌面icon右上角展示小红点
            mChannel.setShowBadge(true);
            getManager().createNotificationChannel(mChannel);
        }
    }

    /**
     * 显示Notification
     */
    public void showNotification(String content, String apkUrl) {

        Intent myIntent = new Intent(mContext, DownloadService.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        myIntent.putExtra(ApiConfig.DOWNLOAD_URL, apkUrl);
        PendingIntent pendingIntent = PendingIntent.getService(mContext, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = getNofity(content)
                .setContentIntent(pendingIntent);

        getManager().notify(NOTIFICATION_ID, builder.build());
    }

    /**
     * 不断调用次方法通知通知栏更新进度条
     */
    public void updateProgress(int progress) {

        String text = "正在下载：" + progress + "%";

        PendingIntent pendingintent = PendingIntent.getActivity(mContext, 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = getNofity(text)
                .setProgress(100, progress, false)
                .setContentIntent(pendingintent);

        getManager().notify(NOTIFICATION_ID, builder.build());
    }

    private NotificationCompat.Builder getNofity(String text) {
        return new NotificationCompat.Builder(mContext.getApplicationContext(), CHANNEL_ID)
                .setTicker("发现新版本，点击进行升级")
                .setContentTitle("版本更新")
                .setContentText(text)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setWhen(System.currentTimeMillis())
                .setPriority(NotificationCompat.PRIORITY_HIGH);

    }

    public void cancel() {
        getManager().cancel(NOTIFICATION_ID);
    }


    private NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }
}
