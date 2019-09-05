package com.zz.recycleviewmvptest.bean;

import com.zz.recycleviewmvptest.widget.Utils;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * author: wuyangyi
 * date: 2019-09-05
 * 象棋对局列表
 */
@Entity
public class ChessListBean {
    @Id(autoincrement = true)
    private Long id;
    private long create_time;
    private String start_time;//开始时间
    private String end_time;//结束时间
    private String time_long; //时间间隔
    private String winner; //获胜者

    @Generated(hash = 1239055159)
    public ChessListBean(Long id, long create_time, String start_time,
            String end_time, String time_long, String winner) {
        this.id = id;
        this.create_time = create_time;
        this.start_time = start_time;
        this.end_time = end_time;
        this.time_long = time_long;
        this.winner = winner;
    }

    public ChessListBean(String win) {
        this.winner = win;
        create_time = System.currentTimeMillis();
    }

    @Generated(hash = 1107225563)
    public ChessListBean() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getTime_long() {
        return time_long;
    }

    public void setTime_long(String time_long) {
        this.time_long = time_long;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
