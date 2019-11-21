package com.zz.recycleviewmvptest.network.download;

import android.util.Log;

/**
 * author: wuyangyi
 * date: 2019-11-21
 * 下载信息
 */
public class DownInfo {
    //存储位置
    private String savePath;
    //下载url
    private String url;
    //基础url
    private String baseUrl;
    //文件总长度
    private long countLength;
    //下载长度
    private long readLength;
    //下载唯一的HttpDownloadService
    private HttpDownloadService service;
    //超时设置
    private int DEFAULT_TIMEOUT = 6;
    //下载状态
    private DownState state;

    public DownState getState() {
        return state;
    }

    public void setState(DownState state) {
        this.state = state;
    }

    public DownInfo(String url) {
        setUrl(url);
        setBaseUrl(getBasUrl(url));
    }

    public DownInfo() {

    }

    public int getConnectionTime() {
        return DEFAULT_TIMEOUT;
    }

    public void setConnectionTime(int DEFAULT_TIMEOUT) {
        this.DEFAULT_TIMEOUT = DEFAULT_TIMEOUT;
    }


    public HttpDownloadService getService() {
        return service;
    }

    public void setService(HttpDownloadService service) {
        this.service = service;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        setBaseUrl(getBasUrl(url));
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public long getCountLength() {
        return countLength;
    }

    public void setCountLength(long countLength) {
        this.countLength = countLength;
    }


    public long getReadLength() {
        return readLength;
    }

    public void setReadLength(long readLength) {
        this.readLength = readLength;
    }

    /**
     * 读取baseurl
     */
    private String getBasUrl(String url) {
        Log.d("原来的url：", url);
        String head = "";
        int index = url.indexOf("://");
        if (index != -1) {
            head = url.substring(0, index + 3);
            url = url.substring(index + 3);
        }
        index = url.indexOf("/");
        if (index != -1) {
            url = url.substring(0, index + 1);
        }
        Log.d("基础url：", head + url);
        return head + url;
    }
}
