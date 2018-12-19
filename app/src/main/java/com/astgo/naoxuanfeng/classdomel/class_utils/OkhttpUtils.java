package com.astgo.naoxuanfeng.classdomel.class_utils;


import android.os.Handler;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/10/9.
 */

public class OkhttpUtils {
    private static Handler mhandler;
    private final OkHttpClient ok;
    private static OkhttpUtils okhttpUtils;


    public OkhttpUtils() {


        ok = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
        mhandler = new Handler();


    }

    //回调的方法
    public static OkhttpUtils getshu() {
        if (okhttpUtils == null) {
            okhttpUtils = new OkhttpUtils();

        }
        return okhttpUtils;
    }

    //接口的固定模式
    public interface fun1 {
        void onres(String string);
    }

    private static void onsuccess(final String string, final fun1 callback) {
        mhandler.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onres(string);
                }
            }
        });
    }

    public void say(String url, final fun1 callback) {
        Request build = new Request.Builder().url(url).build();
        ok.newCall(build).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                onsuccess(response.body().string(), callback);
            }
        });
    }


}