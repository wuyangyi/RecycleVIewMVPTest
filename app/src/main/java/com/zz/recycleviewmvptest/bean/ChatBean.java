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
    private String imagePath; //发送的图片地址
    private String soundPath; //语言地址
    private float soundTime; //语音时长
    @Convert(converter = MyUserConverter.class, columnType = String.class)
    private MyInfoBean myInfoBean; //发件人信息

    public ChatBean(String context) {
        this.context = context;
        create_time = System.currentTimeMillis();
        send_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }



    @Generated(hash = 1872716502)
    public ChatBean() {
    }



    @Generated(hash = 1262334286)
    public ChatBean(Long id, FlListBean.ResultsListBean user, long create_time,
            String send_time, String context, boolean isMe, String userId, String imagePath,
            String soundPath, float soundTime, MyInfoBean myInfoBean) {
        this.id = id;
        this.user = user;
        this.create_time = create_time;
        this.send_time = send_time;
        this.context = context;
        this.isMe = isMe;
        this.userId = userId;
        this.imagePath = imagePath;
        this.soundPath = soundPath;
        this.soundTime = soundTime;
        this.myInfoBean = myInfoBean;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getSoundPath() {
        return soundPath;
    }

    public void setSoundPath(String soundPath) {
        this.soundPath = soundPath;
    }

    public float getSoundTime() {
        return soundTime;
    }

    public void setSoundTime(float soundTime) {
        this.soundTime = soundTime;
    }

    public MyInfoBean getMyInfoBean() {
        return myInfoBean;
    }

    public void setMyInfoBean(MyInfoBean myInfoBean) {
        this.myInfoBean = myInfoBean;
    }

    public static class UserConverter extends BaseConvert<FlListBean.ResultsListBean> {

    }

    public static class MyUserConverter extends BaseConvert<MyInfoBean> {

    }
}
