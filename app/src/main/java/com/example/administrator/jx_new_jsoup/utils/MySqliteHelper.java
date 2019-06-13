package com.example.administrator.jx_new_jsoup.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqliteHelper extends SQLiteOpenHelper {
    private String tb_news="create table tb_news(id Integer primary key autoincrement,title,time,url)";
    private String tb_prictrue="create table tb_prictrue(id Integer primary key autoincrement,title,url)";
    private String tb_video="create table tb_video(id Integer primary key autoincrement,title,time,url,imageurl)";
    private String tb_sc="create table tb_sc(id Integer primary key autoincrement,type,typeId)";
    public MySqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tb_news);
        db.execSQL(tb_prictrue);
        db.execSQL(tb_video);
        db.execSQL(tb_sc);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
