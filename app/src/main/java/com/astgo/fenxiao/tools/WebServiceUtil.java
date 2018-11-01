package com.astgo.fenxiao.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.astgo.fenxiao.MyApplication;
import com.astgo.fenxiao.R;
import com.astgo.fenxiao.bean.AppConstant;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WebServiceUtil {

    private static RequestQueue requestQueue;
    private static ImageLoader imageLoader;

    /**
     * 通过网络加载图片
     *
     * @param imageView 放置图片的控件
     * @param imageURL  图片的URL
     */
    public static void setWebImage(Context context ,ImageView imageView, String imageURL) {
        boolean isFileExist = ComTools.isFileExist(imageURL, context);
        if (isFileExist) {
            //如果有本地缓存读本地缓存
            Bitmap bitmap = ComTools.getBitmap2(imageURL, context);
            imageView.setImageBitmap(bitmap);

        }else {
            //如果没有本地缓存，再通过vollery调用
            imageLoader = MyApplication.getInstance().getImageLoader();
            ImageLoader.ImageListener imageListener = imageLoader.getImageListener(imageView,
                    R.mipmap.icon_banner_loadding, R.mipmap.icon_banner_loadding);
            imageLoader.get(imageURL, imageListener);
        }
    }

    /**
     * get发送json请求
     */
    public static void getJsonData(String url, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        Log.d("MyApp", "GET:" + url);
        requestQueue = MyApplication.getInstance().getRequestQueue();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, listener, errorListener);
        requestQueue.add(jsonObjectRequest);
    }

    /**
     * post发送String请求
     */
    public static void postStringData(String url,final Map<String, String> param, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        Log.d("MyApp", "POST:" + url);
        requestQueue = MyApplication.getInstance().getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, listener, errorListener){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
    /**
     * post发送Json请求
     */
    public static void postJsonData(String url, JSONObject jsonObject, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        Log.d("MyApp", "POST:" + url);
        requestQueue = MyApplication.getInstance().getRequestQueue();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, jsonObject, listener, errorListener);
        requestQueue.add(jsonObjectRequest);
    }



    /**
     * 跳转到网页Activity的方法
     */
//    public static void transNewActivity(Context context, String url) {
//        Map<String, String> map = new HashMap<>();
//        map.put(MyWebViewActivity.WEB_TITLE, " ");
//        map.put(MyWebViewActivity.WEB_URL, url);
//        Intent intent = new Intent(context , MyWebViewActivity.class);
//        for (String s : map.keySet()) {
//            intent.putExtra(s, map.get(s));
//        }
//        context.startActivity(intent);
//    }
//
//    public static void transNewActivity(Context context, String url, String title, Class<?> cls) {
//        Map<String, String> map = new HashMap<>();
//        map.put(MyWebViewActivity.WEB_TITLE, title);
//        map.put(MyWebViewActivity.WEB_URL, url);
//        Intent intent = new Intent(context, cls);
//        for (String s : map.keySet()) {
//            intent.putExtra(s, map.get(s));
//        }
//        ((Activity)context).startActivityForResult(intent, AppConstant.startResultTag);
//    }

}
