package com.zz.recycleviewmvptest.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.zz.recycleviewmvptest.widget.JSONUtil;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * author: wuyangyi
 * date: 2019-09-18
 */
public class RecommendBean extends BaseListBean implements Parcelable, Serializable {


    /**
     * _id : 56cc6d23421aa95caa707a69
     * createdAt : 2015-08-06T07:15:52.65Z
     * desc : 类似Link Bubble的悬浮式操作设计
     * publishedAt : 2015-08-07T03:57:48.45Z
     * type : Android
     * url : https://github.com/recruit-lifestyle/FloatingView
     * used : true
     * who : mthli
     */

    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String type;
    private String url;
    private boolean used;
    private String who;

    public RecommendBean(){}

    public RecommendBean(JSONObject jsonObject) {
        if (jsonObject != null) {
            this._id = JSONUtil.getStringForJSONObject(jsonObject, "_id", "");
            this.createdAt = JSONUtil.getStringForJSONObject(jsonObject, "createdAt", "");
            this.desc = JSONUtil.getStringForJSONObject(jsonObject, "desc", "");
            this.publishedAt = JSONUtil.getStringForJSONObject(jsonObject, "publishedAt", "");
            this.type = JSONUtil.getStringForJSONObject(jsonObject, "type", "");
            this.url = JSONUtil.getStringForJSONObject(jsonObject, "url", "");
            this.used = JSONUtil.getBooleanForJSONObject(jsonObject, "used", false);
            this.who = JSONUtil.getStringForJSONObject(jsonObject, "who", "");
        }
    }

    protected RecommendBean(Parcel in) {
        _id = in.readString();
        createdAt = in.readString();
        desc = in.readString();
        publishedAt = in.readString();
        type = in.readString();
        url = in.readString();
        used = in.readByte() != 0;
        who = in.readString();
    }

    public static final Creator<RecommendBean> CREATOR = new Creator<RecommendBean>() {
        @Override
        public RecommendBean createFromParcel(Parcel in) {
            return new RecommendBean(in);
        }

        @Override
        public RecommendBean[] newArray(int size) {
            return new RecommendBean[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(createdAt);
        dest.writeString(desc);
        dest.writeString(publishedAt);
        dest.writeString(type);
        dest.writeString(url);
        dest.writeByte((byte) (used ? 1 : 0));
        dest.writeString(who);
    }
}
