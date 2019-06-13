package com.example.administrator.jx_new_jsoup.entity;

import java.io.Serializable;

public class Prictrue implements Serializable {
    private String name;
    private String content;
    private String time;
    private String imageurl;

    public Prictrue() {
    }

    public Prictrue(String name, String content, String time, String imageurl) {
        this.name = name;
        this.content = content;
        this.time = time;
        this.imageurl = imageurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getcontent() {
        return content;
    }

    public void setcontent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
