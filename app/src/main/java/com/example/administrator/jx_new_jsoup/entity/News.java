package com.example.administrator.jx_new_jsoup.entity;

import java.io.Serializable;

public class News implements Serializable {
    private String title;
    private String time;
    private String url;

    public News(String title, String time, String url) {
        this.title = title;
        this.time = time;
        this.url = url;
    }

    public News() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
