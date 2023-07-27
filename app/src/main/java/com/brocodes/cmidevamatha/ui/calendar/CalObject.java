package com.brocodes.cmidevamatha.ui.calendar;

public class CalObject {
    private String biblereading;
    private String birthday;
    private String feastday;
    private String cmiobituary;
    private String dailysaints;
    private String finalvow;
    private String firstvow;
    private String ordination;
    private String othermarks;

    public CalObject() {
    }

    public CalObject(String biblereading, String birthday, String feastday, String cmiobituary, String dailysaints, String finalvow, String firstvow, String ordination, String othermarks) {
        this.biblereading = biblereading;
        this.birthday = birthday;
        this.feastday = feastday;
        this.cmiobituary = cmiobituary;
        this.dailysaints = dailysaints;
        this.finalvow = finalvow;
        this.firstvow = firstvow;
        this.ordination = ordination;
        this.othermarks = othermarks;
    }

    public String getBiblereading() {
        return biblereading;
    }

    public void setBiblereading(String biblereading) {
        this.biblereading = biblereading;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getFeastday() {
        return feastday;
    }

    public void setFeastday(String feastday) {
        this.feastday = feastday;
    }

    public String getCmiobituary() {
        return cmiobituary;
    }

    public void setCmiobituary(String cmiobituary) {
        this.cmiobituary = cmiobituary;
    }

    public String getDailysaints() {
        return dailysaints;
    }

    public void setDailysaints(String dailysaints) {
        this.dailysaints = dailysaints;
    }

    public String getFinalvow() {
        return finalvow;
    }

    public void setFinalvow(String finalvow) {
        this.finalvow = finalvow;
    }

    public String getFirstvow() {
        return firstvow;
    }

    public void setFirstvow(String firstvow) {
        this.firstvow = firstvow;
    }

    public String getOrdination() {
        return ordination;
    }

    public void setOrdination(String ordination) {
        this.ordination = ordination;
    }

    public String getOthermarks() {
        return othermarks;
    }

    public void setOthermarks(String othermarks) {
        this.othermarks = othermarks;
    }
}
