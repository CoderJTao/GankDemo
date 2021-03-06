package com.jtao.gankdemo.Activity.Util;

public class GankApi {

    private static String API_GANK_HOST = "http://gank.io";

    // 获取当日最新数据
    private static String API_TODAY = "/api/today";

    // 首页数据日期
    private static String API_NEW_DATE = "/api/day/history";

    // 首页数据    api/day/2019/01/21
    private static String API_NEW_DATA = "/api/day";

    // 搜索数据
    private static String API_SEARCH = "/api/search/query";

    // 分类全部数据  /api/data/all/20/1
    private static String API_CATEGORY = "/api/data/";

    // 分类数据     /api/data/瞎推荐/10/1
    private static String API_CATEGORY_DATA = "/api/data/瞎推荐";

    // 妹纸数据    /api/data/福利/10/1
    private static String API_GIRL_DATA = "/api/data/福利";


    public static String TODAY() {
        return API_GANK_HOST + API_TODAY;
    }

    public static String HISTORYLIST() {
        return API_GANK_HOST + API_NEW_DATE;
    }

    public static String DAYDATA(final String s) {
        return API_GANK_HOST + API_NEW_DATA;
    }

    public static String SEARCHDATA(final String keyword) {
        return API_GANK_HOST + API_SEARCH + "/" + keyword + "/category/all/count/50/page/1";
    }

    public static String HISTORYDATALIST(final String date) {
        String[] arr = date.split("-");
        return API_GANK_HOST + API_NEW_DATA + "/" + arr[0] + "/" + arr[1] + "/" + arr[2];
    }

    public static String GIRLS() {
        return API_GANK_HOST + API_GIRL_DATA + "/20/1";
    }

    public static String CATEGORY(final String category) {
        return API_GANK_HOST + API_CATEGORY + category + "/50/1";
    }
}


