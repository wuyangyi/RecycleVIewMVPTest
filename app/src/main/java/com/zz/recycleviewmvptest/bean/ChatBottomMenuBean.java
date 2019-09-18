package com.zz.recycleviewmvptest.bean;

/**
 * author: wuyangyi
 * date: 2019-09-17
 */
public class ChatBottomMenuBean {
    private String title;
    private int image;

    public ChatBottomMenuBean(String title, int image) {
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
