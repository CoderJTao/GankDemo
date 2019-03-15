package com.jtao.gankdemo.Activity.Model;

import com.squareup.moshi.Json;

import java.util.List;

public class NewsSubMoshi {
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
}
