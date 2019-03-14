package com.jtao.gankdemo.Activity.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
* /**
 *
 *
 * _id: "5c8747cf9d2122032f6b5aaf",
 * createdAt: "2019-03-12T05:46:55.816Z",
 * desc: "Flutter日益恒行，一篇入门级BaseWidget项目架构值得您的关注~",
 * images: [
 * "http://img.gank.io/2aff8927-bd5d-4d8f-9fac-6e4ed2013c87"
 * ],
 * publishedAt: "2019-03-13T01:32:11.450Z",
 * source: "web",
 * type: "Android",
 * url: "https://blog.csdn.net/iamdingruihaha/article/details/88319883",
 * used: true,
 * who: "潇湘剑雨"
 *
 *
 */

public class NewsMoshi {

    private JSONObject mObject;

    public List<String> tagsLists;
    public Map<String, List<NewsSubMoshi>> listMap;


    public NewsMoshi(JSONArray categoryList, JSONObject object) throws JSONException, IOException {
        this.mObject = object;

        tagsLists = new ArrayList<>();
        for (int i = 0; i < categoryList.length(); i++) {
            String tag = categoryList.getString(i);
            tagsLists.add(tag);
        }

        listMap = new HashMap<String, List<NewsSubMoshi>>();
        setSubModels();
    }

    private void setSubModels() throws JSONException, IOException {
        for (Iterator<String> it = mObject.keys(); it.hasNext(); ) {
            String key = it.next();

            if (key.equals("福利")) {
                continue;
            }

            JSONArray lists = mObject.getJSONArray(key);

            List<NewsSubMoshi> subLists = new ArrayList<>();
            for (int j = 0; j < lists.length(); j++) {
                String jsonStr = lists.getJSONObject(j).toString();

                Moshi moshi = new Moshi.Builder().build();
                JsonAdapter<NewsSubMoshi> jsonAdapter = moshi.adapter(NewsSubMoshi.class);

                NewsSubMoshi item = jsonAdapter.fromJson(jsonStr);
                subLists.add(item);
            }
            listMap.put(key, subLists);
        }
    }




}
