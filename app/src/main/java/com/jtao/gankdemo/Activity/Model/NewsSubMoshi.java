package com.jtao.gankdemo.Activity.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.squareup.moshi.Json;

import java.util.List;

public class NewsSubMoshi implements Parcelable {
    @Json(name = "_id")
    public String newsId;

    @Json(name = "createdAt")
    public String createdAt;

    @Json(name = "desc")
    public String desc;

    @Json(name = "images")
    public List<String> images;

    @Json(name = "publishedAt")
    public String publishedAt;

    @Json(name = "source")
    public String source;

    @Json(name = "type")
    public String type;

    @Json(name = "url")
    public String url;

    @Json(name = "used")
    public boolean used;

    @Json(name = "who")
    public String who;

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     *  将想要传递的数据写入到 Parcel 容器中
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(newsId);
        dest.writeString(createdAt);
        dest.writeString(desc);
        dest.writeStringList(images);
        dest.writeString(publishedAt);
        dest.writeString(source);
        dest.writeString(type);
        dest.writeString(url);
        dest.writeString(who);
    }

    public static final Creator<NewsSubMoshi> CREATOR = new Creator<NewsSubMoshi>() {

        /**
         *  用于将写入 Parcel 容器中的数据读出来，用读出来的数据实例化一个对象，并且返回
         * @param source
         * @return
         */
        @Override
        public NewsSubMoshi createFromParcel(Parcel source) {
            NewsSubMoshi moshi = new NewsSubMoshi();
            moshi.newsId = source.readString();
            moshi.createdAt = source.readString();
            moshi.desc = source.readString();
            moshi.images = source.createStringArrayList();
            moshi.publishedAt = source.readString();
            moshi.source = source.readString();
            moshi.type = source.readString();
            moshi.url = source.readString();
            moshi.who = source.readString();

            return moshi;
        }

        /**
         * 创建一个长度为 size 的数组并且返回,供外部类反序列化本类数组使用
         * @param size
         * @return
         */
        @Override
        public NewsSubMoshi[] newArray(int size) {
            return new NewsSubMoshi[size];
        }
    };
}
