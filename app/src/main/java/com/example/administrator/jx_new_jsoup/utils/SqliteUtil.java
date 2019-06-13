package com.example.administrator.jx_new_jsoup.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.jx_new_jsoup.entity.News;
import com.example.administrator.jx_new_jsoup.entity.Prictrue;
import com.example.administrator.jx_new_jsoup.entity.Sc;
import com.example.administrator.jx_new_jsoup.entity.Video;

import java.util.ArrayList;
import java.util.List;

public class SqliteUtil {
    private static MySqliteHelper helper;
    private static SQLiteDatabase db;
    private static SqliteUtil util;

    public static void initDB(Context context, String DBName) {
        if (helper == null) {
            helper = new MySqliteHelper
                    (context, DBName,
                            null, 1);
        }
        if (db == null) {
            db = helper.getWritableDatabase();
        }
    }

    public static SqliteUtil getInstance() {
        if (util == null) {
            util = new SqliteUtil();
        }
        return util;
    }

    public List<Sc> getScList() {
        List<Sc> oList = new ArrayList<>();
        Cursor cursor = db.query("tb_sc",
                null,
                null,
                null,
                null,
                null,
                null);
        while (cursor.moveToNext()) {
            int type = cursor.getInt
                    (cursor.getColumnIndex("type"));
            int typeId = cursor.getInt
                    (cursor.getColumnIndex("typeId"));
            Cursor c;
            Sc sc = new Sc();
            sc.setType(type);
            if (type == 1) {
                c = db.rawQuery("select * from tb_news where id=" + typeId,
                        new String[]{});
                if (c.moveToNext()){
                    String title = c.getString(c.getColumnIndex("title"));
                    String time = c.getString(c.getColumnIndex("time"));
                    String url = c.getString(c.getColumnIndex("url"));
                    News news = new News(title, time, url);
                    sc.setObject(news);
                }
            } else if (type == 2) {
                c = db.rawQuery("select * from tb_prictrue where id=" + typeId,
                        new String[]{});
                if (c.moveToNext()){
                    String title = c.getString(c.getColumnIndex("title"));
                    String url = c.getString(c.getColumnIndex("url"));
                    Prictrue prictrue = new Prictrue(title, "", "", url);
                    sc.setObject(prictrue);
                }
            } else if (type == 3) {
                c = db.rawQuery("select * from tb_video where id=" + typeId,
                        new String[]{});
               if (c.moveToNext()){
                   String title = c.getString(c.getColumnIndex("title"));
                   String time = c.getString(c.getColumnIndex("time"));
                   String url = c.getString(c.getColumnIndex("url"));
                   String imageurl = c.getString(c.getColumnIndex("imageurl"));
                   Video video = new Video(imageurl, title, time, url);
                   sc.setObject(video);
               }
            }
            oList.add(sc);
        }
        return oList;
    }

    public long delete(String table, String title, String value, int type) {
        long i = 0;
        Cursor cursor = db.query(table, new String[]{"id"},
                title + "=?", new String[]{value},
                null, null, null);
        if (cursor.moveToNext()) {
            int id = cursor.getInt(
                    cursor.getColumnIndex("id"));
            int j = db.delete(table, "id=" + id, new String[]{});
            if (j > 0) {
                i = db.delete("tb_sc",
                        "type=" + type + " and typeId=" + id,
                        new String[]{});
            }
        }
        return i;
    }

    public long insert(String table, Object o, int type) {
        long i = 0;
        ContentValues values = new ContentValues();
        switch (type) {
            case 1:
                News news = (News) o;
                values.put("title", news.getTitle());
                values.put("time", news.getTime());
                values.put("url", news.getUrl());
                break;
            case 2:
                Prictrue prictrue = (Prictrue) o;
                values.put("title", prictrue.getName());
                values.put("url", prictrue.getImageurl());
                break;
            case 3:
                Video video = (Video) o;
                values.put("title", video.getVideoname());
                values.put("time", video.getVideotime());
                values.put("imageurl", video.getVideoimageurl());
                values.put("url", video.getVideodetailurl());
                break;
        }
        long id = db.insert(table, null, values);
        ContentValues values1 = new ContentValues();
        values1.put("type", type);
        values1.put("typeId", id);
        i = db.insert("tb_sc",
                null, values1);
        return i;
    }

    public boolean isSC(String table, String title, String value) {
        Cursor cursor = db.query(table,
                null,
                title + "=?",
                new String[]{value},
                null,
                null,
                null);
        if (cursor.moveToNext()) {
            return true;
        }

        return false;
    }

}
