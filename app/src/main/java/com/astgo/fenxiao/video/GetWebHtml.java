//package com.astgo.fenxiao.video;
//
//import android.app.Activity;
//import android.os.AsyncTask;
//import android.text.TextUtils;
//
//import org.jsoup.Connection;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//
//import java.io.IOException;
//
///**
// * Created by ast009 on 2018/1/20.
// */
//
//public class GetWebHtml extends AsyncTask<Void, Void, String> {
//
//    private Activity activity;
//    private String url;
//    private String cookie;
//
//    public GetWebHtml(Activity activity, String url, String cookie) {
//        this.activity = activity;
//        this.url = url;
//        this.cookie = cookie;
//    }
//
//
//    @Override
//    protected String doInBackground(Void... voids) {
//        Connection localConnection = Jsoup.connect(this.url);
//        localConnection.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
//        localConnection.header("Accept-Encoding", "gzip, deflate");
//        localConnection.header("Accept-Language", "zh-TW,zh;q=0.8,en-US;q=0.6,en;q=0.4,zh-CN;q=0.2");
//        localConnection.header("User-Agent", "Mozilla/5.0 (Linux; Android 5.1; vivo X6D Build/LMY47I) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/39.0.0.0 Mobile Safari/537.36");
//        if ((!TextUtils.isEmpty(this.cookie)) && (!this.cookie.trim().equals("null")))
//            localConnection.header("Cookie", this.cookie.trim());
//        try{
//            Document localDocument = localConnection.timeout(10000)
//                    .validateTLSCertificates(false)
//                    .get();
//            if (localDocument != null){
//                if(activity instanceof WebVideoListActivity){
//                    String str = ((WebVideoListActivity) activity).modifyHtml(url,localDocument);
//                    return str;
//                }
//            }
//        }catch (IOException localIOException){
//            localIOException.printStackTrace();
//            return "";
//        }
//        return "";
//    }
//
//    @Override
//    protected void onPostExecute(String s) {
//        super.onPostExecute(s);
//        ((WebVideoListActivity) activity).loadDataWithBaseUrl(s);
//    }
//}
