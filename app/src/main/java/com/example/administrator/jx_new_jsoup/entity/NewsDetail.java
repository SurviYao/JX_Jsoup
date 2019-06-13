package com.example.administrator.jx_new_jsoup.entity;

import java.util.List;

public class NewsDetail {
    private String title;
    private String time;
    private String author;
    private List<String> content;

    public NewsDetail(String title, String time, String author, List<String> content) {
        this.title = title;
        this.time = time;
        this.author = author;
        this.content = content;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    public NewsDetail() {
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
