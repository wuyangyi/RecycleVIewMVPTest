package com.zz.recycleviewmvptest.bean;

/**
 * author: wuyangyi
 * date: 2019-11-20
 */
public class AppVersionBean {

    /**
     * versionCode : 3
     * versionName : 1.2
     * updateMessage : 1.解决部分bug。
     2.优化性能，提升用户体验
     3.增加版本更新。
     * downloadUrl :
     */

    private int versionCode;
    private String versionName;
    private String updateMessage;
    private String downloadUrl;

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getUpdateMessage() {
        return updateMessage;
    }

    public void setUpdateMessage(String updateMessage) {
        this.updateMessage = updateMessage;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
