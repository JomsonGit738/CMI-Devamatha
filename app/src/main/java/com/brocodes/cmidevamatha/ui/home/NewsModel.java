package com.brocodes.cmidevamatha.ui.home;

import com.google.firebase.database.ServerValue;

public class NewsModel {

    private String title;
    private String desc;
    private Object timeStamp2 ;

    public NewsModel() {
    }

    public NewsModel(String title, String desc) {

        this.title = title;
        this.desc = desc;
        this.timeStamp2 = ServerValue.TIMESTAMP;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Object getTimeStamp2() {
        return timeStamp2;
    }

    public void setTimeStamp2(Object timeStamp2) {
        this.timeStamp2 = timeStamp2;
    }
}
