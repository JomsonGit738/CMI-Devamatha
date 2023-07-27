package com.brocodes.cmidevamatha.ui.bulletin.pdf;

public class Pdf_c {
    String title;
    String url;

    public Pdf_c() {
    }

    public Pdf_c(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
