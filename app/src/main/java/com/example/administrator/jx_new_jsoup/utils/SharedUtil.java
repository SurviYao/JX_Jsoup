package com.example.administrator.jx_new_jsoup.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class SharedUtil {
    private static SharedUtil util;
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    public static void init(Context context, String filename) {
        if (preferences == null) {
            preferences = context
                    .getSharedPreferences
                            (filename, Context.MODE_PRIVATE);
        }
        if (editor == null) {
            editor = preferences.edit();
        }
    }

    public static SharedUtil getInstance() {
        if (util == null) {
            util = new SharedUtil();
        }
        return util;
    }

    public SharedUtil setString(String key, String values) {
        editor.putString(key, values);
        editor.commit();
        return util;
    }

    public String getString(String key) {
        return preferences.getString(key, "");
    }

    public SharedUtil setBoolean(String key, boolean values) {
        editor.putBoolean(key, values);
        editor.commit();
        return util;
    }

    public boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

    public SharedUtil setLong(String key, long values) {
        editor.putLong(key, values);
        editor.commit();
        return util;
    }

    public long getLong(String key) {
        return preferences.getLong(key, 0);
    }

    public SharedUtil setInt(String key, int values) {
        editor.putInt(key, values);
        editor.commit();
        return util;
    }

    public int getInt(String key) {
        return preferences.getInt(key, 0);
    }

    public SharedUtil setFloat(String key, float values) {
        editor.putFloat(key, values);
        editor.commit();
        return util;
    }

    public float getFloat(String key) {
        return preferences.getFloat(key, 0);
    }

    public SharedUtil setStringSet(String key, Set<String> values) {
        editor.putStringSet(key, values);
        editor.commit();
        return util;
    }

    public Set<String> getStringSet(String key) {
        return preferences.getStringSet(key, null);
    }

}
