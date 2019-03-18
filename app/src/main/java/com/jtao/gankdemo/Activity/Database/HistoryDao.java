package com.jtao.gankdemo.Activity.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class HistoryDao {
    private SQLiteDatabase db;

    public HistoryDao(SQLiteDatabase sqLiteDatabase) {
        this.db = sqLiteDatabase;
    }

    // 插入数据
    public boolean insert(String history) {

        ContentValues contentValues = new ContentValues();

        contentValues.put("history", history);

        long insertResult = db.insert("search_history", null, contentValues);

        if (insertResult == -1) {
            return false;
        }

        return true;
    }

    // 删除数据
    public boolean delete(String history) {
        if (history == null || history.equals("")) {
            db.delete("search_history", null, null);
            return true;
        }

        int deleteResult = db.delete("search_history", "text=?", new String[]{history + ""});

        if (deleteResult == 0) {
            return false;
        }
        return true;
    }

    // 查找数据
    public List<String>queryAll() {
        List<String> historyList = new ArrayList<>();

        Cursor cursor = db.query("search_history", null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String history = cursor.getString(1);

            historyList.add(history);
        }
        return historyList;
    }


}
