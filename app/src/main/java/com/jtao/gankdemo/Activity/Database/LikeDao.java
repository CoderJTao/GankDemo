package com.jtao.gankdemo.Activity.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jtao.gankdemo.Activity.Model.NewsSubMoshi;

import java.util.ArrayList;
import java.util.List;

public class LikeDao {
    private OpenHelper_like openHelper;

    public LikeDao(Context context) {
        this.openHelper = new OpenHelper_like(context);
    }

    // 插入数据
    public boolean insert(NewsSubMoshi item) {

        ContentValues contentValues = new ContentValues();

        contentValues.put("newsId", item.newsId);
        contentValues.put("item_desc", item.desc);

        if (item.images != null && item.images.size() > 0) {
            StringBuilder builder = new StringBuilder();
            for (String s: item.images) {
                builder.append(s);
                builder.append("||");
            }
            contentValues.put("images", builder.toString());
        }

        contentValues.put("publishedAt", item.publishedAt);
        contentValues.put("source", item.source);
        contentValues.put("type", item.type);
        contentValues.put("url", item.url);
        contentValues.put("who", item.who);

        SQLiteDatabase db = openHelper.getWritableDatabase();
        long insertResult = db.insert("user_like", null, contentValues);

        if (insertResult == -1) {
            return false;
        }

        return true;
    }

    // 删除数据
    public boolean delete(NewsSubMoshi item) {
        SQLiteDatabase db = openHelper.getReadableDatabase();

        if (item == null) {
            db.delete("user_like", null, null);
            return true;
        }

        int deleteResult = db.delete("user_like", "newsId=?", new String[]{item.newsId + ""});

        if (deleteResult == 0) {
            return false;
        }
        return true;
    }

    // 查找数据
    public List<NewsSubMoshi> queryAll() {
        SQLiteDatabase db = openHelper.getReadableDatabase();

        List<NewsSubMoshi> list = new ArrayList<>();

        Cursor cursor = db.query("user_like", null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            NewsSubMoshi item = new NewsSubMoshi();

            item.newsId = cursor.getString(cursor.getColumnIndex("newsId"));
            item.desc = cursor.getString(cursor.getColumnIndex("item_desc"));

            List<String> images = new ArrayList<>();
            String s = cursor.getString(cursor.getColumnIndex("images"));
            if (s != null) {
                String[] arr = s.split("||");
                if (arr.length > 1) {
                    for (int i = 0; i < arr.length; i++) {
                        images.add(arr[i]);
                    }
                }
                item.images = images;
            }

            item.publishedAt = cursor.getString(cursor.getColumnIndex("publishedAt"));
            item.source = cursor.getString(cursor.getColumnIndex("source"));
            item.type = cursor.getString(cursor.getColumnIndex("type"));
            item.url = cursor.getString(cursor.getColumnIndex("url"));
            item.who = cursor.getString(cursor.getColumnIndex("who"));

            list.add(item);
        }
        return list;
    }


}
