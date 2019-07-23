package com.zz.recycleviewmvptest.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ChatBean extends BaseListBean {
    @Id(autoincrement = true)
    private Long id;
    @Convert(converter = UserConverter.class, columnType = String.class)
    private FlListBean.ResultsListBean user;
    private long create_time;
    private String send_time;
    private String context; //发布的内容
    private boolean isMe; //是否是自己发的内容
    private String userId;

    public ChatBean(String context) {
        this.context = context;
        create_time = System.currentTimeMillis();
        send_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    @Generated(hash = 135916805)
    public ChatBean(Long id, FlListBean.ResultsListBean user, long create_time,
            String send_time, String context, boolean isMe, String userId) {
        this.id = id;
        this.user = user;
        this.create_time = create_time;
        this.send_time = send_time;
        this.context = context;
        this.isMe = isMe;
        this.userId = userId;
    }

    @Generated(hash = 1872716502)
    public ChatBean() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FlListBean.ResultsListBean getUser() {
        return user;
    }

    public void setUser(FlListBean.ResultsListBean user) {
        this.user = user;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public String getSend_time() {
        return send_time;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public boolean isMe() {
        return isMe;
    }

    public void setMe(boolean me) {
        isMe = me;
    }

    public boolean getIsMe() {
        return this.isMe;
    }

    public void setIsMe(boolean isMe) {
        this.isMe = isMe;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public static class UserConverter extends BaseConvert<FlListBean.ResultsListBean> {

    }
}
