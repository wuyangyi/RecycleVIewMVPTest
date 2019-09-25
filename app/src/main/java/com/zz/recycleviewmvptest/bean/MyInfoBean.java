package com.zz.recycleviewmvptest.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;


/**
 * author: wuyangyi
 * date: 2019-09-23
 * 当前用户信息
 */
@Entity
public class MyInfoBean extends BaseListBean implements Serializable, Parcelable {
    private static final long serialVersionUID = 4393338023102640914L;

    @Id(autoincrement = true)
    private Long id;

    private String nickname;
    private String school;
    private int age;
    private int sex;
    private String head;
    private long create_time;
    private String password;
    private boolean isLogin;
    private boolean isAdmin; //是否是管理员
    private String phone;

    public MyInfoBean(String phone) {
        this.phone = phone;
        create_time = System.currentTimeMillis();
    }

    protected MyInfoBean(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        nickname = in.readString();
        school = in.readString();
        age = in.readInt();
        sex = in.readInt();
        head = in.readString();
        create_time = in.readLong();
        password = in.readString();
        isLogin = in.readByte() != 0;
        isAdmin = in.readByte() != 0;
        phone = in.readString();
    }



    @Generated(hash = 890789893)
    public MyInfoBean() {
    }

    @Generated(hash = 2138435013)
    public MyInfoBean(Long id, String nickname, String school, int age, int sex,
            String head, long create_time, String password, boolean isLogin,
            boolean isAdmin, String phone) {
        this.id = id;
        this.nickname = nickname;
        this.school = school;
        this.age = age;
        this.sex = sex;
        this.head = head;
        this.create_time = create_time;
        this.password = password;
        this.isLogin = isLogin;
        this.isAdmin = isAdmin;
        this.phone = phone;
    }

    public static final Creator<MyInfoBean> CREATOR = new Creator<MyInfoBean>() {
        @Override
        public MyInfoBean createFromParcel(Parcel in) {
            return new MyInfoBean(in);
        }

        @Override
        public MyInfoBean[] newArray(int size) {
            return new MyInfoBean[size];
        }
    };

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean getIsLogin() {
        return this.isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public boolean getIsAdmin() {
        return this.isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(nickname);
        dest.writeString(school);
        dest.writeInt(age);
        dest.writeInt(sex);
        dest.writeString(head);
        dest.writeLong(create_time);
        dest.writeString(password);
        dest.writeByte((byte) (isLogin ? 1 : 0));
        dest.writeByte((byte) (isAdmin ? 1 : 0));
        dest.writeString(phone);
    }

    @Override
    public String toString() {
        return "MyInfoBean{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", school='" + school + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", head='" + head + '\'' +
                ", create_time=" + create_time +
                ", password='" + password + '\'' +
                ", isLogin=" + isLogin +
                ", isAdmin=" + isAdmin +
                ", phone='" + phone + '\'' +
                '}';
    }
}
