package com.jtao.gankdemo.Activity.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OpenHelper_like extends SQLiteOpenHelper {

    private static final String name = "search_history.db";
    private static final int version = 1;

    private static final String create_db = "CREATE TABLE IF NOT EXISTS " +
            "user_like(newsId varchar(64) primary key, " +
            "item_desc text, images text, " +
            "publishedAt varchar(128), " +
            "source varchar(32), " +
            "type varchar(32), " +
            "url varchar(32), " +
            "who varchar(32))";

    public OpenHelper_like(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            // 修改表，暂时先不用
        }
    }
}
