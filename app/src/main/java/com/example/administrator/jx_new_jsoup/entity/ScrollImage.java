package com.example.administrator.jx_new_jsoup.entity;

public class ScrollImage {
    private String imageurl;
    private String title;

    public ScrollImage(String imageurl, String title) {
        this.imageurl = imageurl;
        this.title = title;
    }

    public ScrollImage() {
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
