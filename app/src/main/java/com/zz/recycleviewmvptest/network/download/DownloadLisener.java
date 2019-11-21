package com.zz.recycleviewmvptest.network.download;

/**
 * author: wuyangyi
 * date: 2019-11-21
 * 下载接口监听
 */
public interface DownloadLisener {
    //开始下载
    void onStart();

    //下载进度
    void onProgress(int progress);

    //下载完成
    void onFinish(String path);
}
