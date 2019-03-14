package com.jtao.gankdemo.Activity.Model;

import com.squareup.moshi.Json;

import java.util.List;

public class NewsSubMoshi {
    @Json(name = "_id")
    String newsId;

    @Json(name = "createdAt")
    String createdAt;

    @Json(name = "desc")
    String desc;

    @Json(name = "images")
    List<String> images;

    @Json(name = "publishedAt")
    String publishedAt;

    @Json(name = "source")
    String source;

    @Json(name = "type")
    String type;

    @Json(name = "url")
    String url;

    @Json(name = "used")
    boolean used;

    @Json(name = "who")
    String who;
}
