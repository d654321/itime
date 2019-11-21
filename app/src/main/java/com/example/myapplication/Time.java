package com.example.myapplication;

import java.io.Serializable;

public class Time implements Serializable {
    public Time(String title, int resourceId,int countdown) { //生成构造函数
        this.setTitle(title);
        this.setResourceId(resourceId);
        this.setCountdown(countdown);
    }

    private String title;  //日期加描述
    private int resourceId;

    public int getCountdown() {
        return countdown;
    }

    public void setCountdown(int countdown) {
        this.countdown = countdown;
    }

    private int countdown; //倒计时剩余时间

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

}
