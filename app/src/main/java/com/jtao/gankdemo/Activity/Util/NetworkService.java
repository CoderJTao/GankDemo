package com.jtao.gankdemo.Activity.Util;

import android.util.Log;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkService {

    private static OkHttpClient okHttpClient = new OkHttpClient();

    /**
     * 获取首页历史日期数据
     *
     * @param callback
     */
    public static void getNewsDates(final MyNetCall callback) {
        String url = GankApi.HISTORYLIST();

        final Request request = new Request.Builder()
                .url(url)
                .get() // 默认就是GET请求，可以不写
                .build();

        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.failed(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callback.success(call, response);
            }
        });
    }

    /**
     * 获取当日最新数据
     *
     * @param callback
     */
    public static void getToDayData(final MyNetCall callback) {
        String url = GankApi.TODAY();

        final Request request = new Request.Builder()
                .url(url)
                .get() // 默认就是GET请求，可以不写
                .build();

        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.failed(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callback.success(call, response);
            }
        });
    }




    /**
     * 自定义网络回调接口
     */
    public interface MyNetCall{
        void success(Call call, Response response) throws IOException;
        void failed(Call call, IOException e);
    }
}
