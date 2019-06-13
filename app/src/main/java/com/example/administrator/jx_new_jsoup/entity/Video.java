package com.example.administrator.jx_new_jsoup.entity;

import java.io.Serializable;

public class Video implements Serializable {
    private String videoimageurl;
    private String videoname;
    private String videotime;
    private String videodetailurl;

    public Video() {
    }

    public Video(String videoimageurl, String videoname, String videotime, String videodetailurl) {
        this.videoimageurl = videoimageurl;
        this.videoname = videoname;
        this.videotime = videotime;
        this.videodetailurl = videodetailurl;
    }

    public String getVideoimageurl() {
        return videoimageurl;
    }

    public void setVideoimageurl(String videoimageurl) {
        this.videoimageurl = videoimageurl;
    }

    public String getVideoname() {
        return videoname;
    }

    public void setVideoname(String videoname) {
        this.videoname = videoname;
    }

    public String getVideotime() {
        return videotime;
    }

    public void setVideotime(String videotime) {
        this.videotime = videotime;
    }

    public String getVideodetailurl() {
        return videodetailurl;
    }

    public void setVideodetailurl(String videodetailurl) {
        this.videodetailurl = videodetailurl;
    }
}
