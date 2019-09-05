package com.zz.recycleviewmvptest.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UserInfoBean extends BaseListBean {
    public final static int MAN_WOMEN = 0; //匿名
    public final static int MAN = 1;  //男
    public final static int WOMEN = 2; //女
    @Id(autoincrement = true)
    private Long id;

    private String nickname;
    private String school;
    private int age;
    private int sex;
    private String head;
    private long create_time;

    public UserInfoBean(String nickname) {
        this.nickname = nickname;
        create_time = System.currentTimeMillis();
    }


    @Generated(hash = 508122196)
    public UserInfoBean(Long id, String nickname, String school, int age, int sex,
            String head, long create_time) {
        this.id = id;
        this.nickname = nickname;
        this.school = school;
        this.age = age;
        this.sex = sex;
        this.head = head;
        this.create_time = create_time;
    }


    @Generated(hash = 1818808915)
    public UserInfoBean() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

}
