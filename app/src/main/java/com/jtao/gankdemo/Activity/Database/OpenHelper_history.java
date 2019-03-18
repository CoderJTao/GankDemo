package com.jtao.gankdemo.Activity.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OpenHelper_history extends SQLiteOpenHelper {

    private static final String name = "search_history.db";
    private static final int version = 1;

    private static final String create_db = "CREATE TABLE IF NOT EXISTS " +
            "search_history(history_id INTEGER primary key autoincrement," +
            "text varchar(128))";

    public OpenHelper_history(Context context) {
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
