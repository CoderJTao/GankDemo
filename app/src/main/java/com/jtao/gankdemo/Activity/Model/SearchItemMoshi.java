package com.jtao.gankdemo.Activity.Model;

import com.squareup.moshi.Json;

public class SearchItemMoshi {

    @Json(name = "desc")
    public String desc;

    @Json(name = "ganhuo_id")
    public String ganhuo_id;

    @Json(name = "publishedAt")
    public String publishedAt;

    @Json(name = "readability")
    public String readability;

    @Json(name = "type")
    public String type;

    @Json(name = "url")
    public String url;

    @Json(name = "who")
    public String who;
}
