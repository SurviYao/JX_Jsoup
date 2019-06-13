package com.example.administrator.jx_new_jsoup.entity;

public class Sc {
    private int type;
    private Object object;

    public Sc() {
    }

    public Sc(int type, Object object) {
        this.type = type;
        this.object = object;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
