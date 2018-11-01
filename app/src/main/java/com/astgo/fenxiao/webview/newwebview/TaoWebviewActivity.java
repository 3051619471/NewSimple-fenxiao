package com.astgo.fenxiao.webview.newwebview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.astgo.fenxiao.MyConstant;
import com.astgo.fenxiao.R;
import com.astgo.fenxiao.activity.BaseActivity;
import com.astgo.fenxiao.bean.AppConstant;
import com.astgo.fenxiao.http.service.HttpClient;
import com.astgo.fenxiao.http.service.HttpResultSubscriber;
import com.astgo.fenxiao.http.service.HttpUtils;
import com.astgo.fenxiao.tools.NetworkUtil;
import com.astgo.fenxiao.tools.utils.DebugUtil;
import com.astgo.fenxiao.tools.utils.SPUtils;
import com.astgo.fenxiao.webview.IWebPageView;
import com.astgo.fenxiao.webview.Js2Java;
import com.astgo.fenxiao.webview.MyWebChromeClient;
import com.astgo.fenxiao.webview.MyWebViewClient;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ast009 on 2017/11/27.
 */

public class TaoWebviewActivity extends BaseActivity implements IWebPageView {

    private WebView webView;
    private MyWebChromeClient mWebChromeClient;
    private String url="";

    private TextView tvTitle;
    //    private String url ="taobao://uland.taobao.com/coupon/edetail?e=yXc1xKMYdSsGQASttHIRqZUw252Yi7vuV1yHWHmoq9X9tBoYohHGgcSQlcWND7udWQL%2Bu0qaRwPQBdkV1ykQ9r9fwBwwUiqlhH9O0aymQ2CBIR5UEq1Lr%2FSs4zOlZ4se&traceId=0bfa8dac15107137617854459e&activityId=58d29cfcd97449adbae1d9f5d9d9d817";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tao_shop);
        getIntentData();
        initview();
    }

    private LinearLayout llNoData;
    private void initview() {
        webView = (WebView) findViewById(R.id.shop_web_view);
        RelativeLayout rlTopTitle = (RelativeLayout) findViewById(R.id.webview_title_top);

        if(!TextUtils.isEmpty(url) && url.startsWith(HttpUtils.API_APP)){
            rlTopTitle.setVisibility(View.GONE);
        }else{
            rlTopTitle.setBackgroundColor(getResources().getColor(R.color.color_new_them));
            tvTitle = (TextView) rlTopTitle.findViewById(R.id.title_tv);
            ImageView ivTitleBack = (ImageView) rlTopTitle.findViewById(R.id.title_left);
            ivTitleBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(webView != null && webView.canGoBack()){
                        webView.goBack();
                    }else{
                        TaoWebviewActivity.this.finish();
                    }
                }
            });
        }

        llNoData = (LinearLayout) findViewById(R.id.ll_no_data);
        webView.setVisibility(View.VISIBLE);
        llNoData.setVisibility( View.GONE);
        initWebView();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(AppConstant.KEY_WEB_URL)){
            url = intent.getStringExtra(AppConstant.KEY_WEB_URL);
        }
    }

    @SuppressLint("JavascriptInterface")
    private void initWebView() {
        WebSettings ws = webView.getSettings();
//        enablecrossdomain();
//        enablecrossdomain41();
        ws.setAllowUniversalAccessFromFileURLs(true);
        // 网页内容的宽度是否可大于WebView控件的宽度
        ws.setLoadWithOverviewMode(false);
        // 保存表单数据
        ws.setSaveFormData(true);
        // 是否应该支持使用其屏幕缩放控件和手势缩放
        ws.setSupportZoom(true);
        ws.setBuiltInZoomControls(true);
        ws.setDisplayZoomControls(false);
        // 启动应用缓存
        ws.setAppCacheEnabled(true);
        // 设置缓存模式
        ws.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // setDefaultZoom  api19被弃用
        // 设置此属性，可任意比例缩放。
        ws.setUseWideViewPort(true);
        // 缩放比例 1
        webView.setInitialScale(1);
        // 告诉WebView启用JavaScript执行。默认的是false。
        ws.setJavaScriptEnabled(true);
        //  页面加载好以后，再放开图片
        ws.setBlockNetworkImage(false);
        // 使用localStorage则必须打开
        ws.setDomStorageEnabled(true);
        // 排版适应屏幕
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        // WebView是否支持多个窗口。
        ws.setSupportMultipleWindows(true);

        // webview从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ws.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        /** 设置字体默认缩放大小(改变网页字体大小,setTextSize  api14被弃用)*/
        ws.setTextZoom(100);

        mWebChromeClient = new MyWebChromeClient(this);
        webView.setWebChromeClient(mWebChromeClient);
        // 与js交互
        //webView.addJavascriptInterface(new Js2Java(this), "js2java");
        webView.setWebViewClient(new MyWebViewClient(this));
        if(NetworkUtil.isConnection(getApplicationContext())){
            serverCheckToken();
        }else {
            loadErrorView(true);
        }
    }

    private void serverCheckToken() {
        Subscription subscription = HttpClient.Builder.getAppService().serverCall(getUrlParam())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultSubscriber<Object>() {
                    @Override
                    public void onSuccess(Object o) {
                        loadNetConnectWebview();
                    }

                    @Override
                    public void _onError(String msg, int code) {
                        DebugUtil.error("msg:"+msg+"-------code"+code);
                        loadNetConnectWebview();
                    }
                });

        addSubscription(subscription);
    }

    private Map<String,String> getUrlParam(){
        Map<String,String> urlParams = new HashMap<>();
        urlParams.put(MyConstant.CALLER, "");
        urlParams.put(MyConstant.CALLED, "");
        return urlParams;
    }


    private void loadNetConnectWebview() {
        Map<String,String> headerMap = new HashMap<>();
        headerMap.put("token", SPUtils.getString(AppConstant.KEY_TOKEN,""));
        if(url.startsWith(HttpUtils.API_APP)){
            webView.loadUrl(url,headerMap);
        }else{
            webView.loadUrl(url);
        }
    }

    public void loadErrorView(boolean showErrorText) {
        if(url.startsWith(HttpUtils.API_APP)){
            return;
        }
        webView.setVisibility(View.GONE);
        llNoData.setVisibility( View.VISIBLE);
        ImageView imError = (ImageView) llNoData.findViewById(R.id.im_error);
            TextView tvError = (TextView) llNoData.findViewById(R.id.tv_error);
        if(showErrorText){
            tvError.setVisibility(View.VISIBLE);
        }else{
            tvError.setVisibility(View.GONE);
        }
        Glide.with(this).load(R.drawable.gif_error).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imError);
    }

    @Override
    public void showWebView() {
        webView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hindWebView() {
        webView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void startProgress() {
        loadErrorView(false);
    }

    @Override
    public void progressChanged(int newProgress) {
        if(newProgress >=90){
            webView.setVisibility(View.VISIBLE);
            llNoData.setVisibility( View.GONE);
        }else{
            loadErrorView(false);
        }
    }

    @Override
    public void addImageClickListener() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            ViewGroup parent = (ViewGroup) webView.getParent();
            if (parent != null) {
                parent.removeView(webView);
            }
            webView.removeAllViews();
            webView.loadUrl("about:blank");
            webView.stopLoading();
            webView.setWebChromeClient(null);
            webView.setWebViewClient(null);
            webView.destroy();
            webView = null;
        }
    }


    public void enablecrossdomain() {
        try {
            Field field = WebView.class.getDeclaredField("mWebViewCore");
            field.setAccessible(true);
            Object webviewcore = field.get(this);
            Method method = webviewcore.getClass().getDeclaredMethod(
                    "nativeRegisterURLSchemeAsLocal", String.class);
            method.setAccessible(true);
            method.invoke(webviewcore, "http");
            method.invoke(webviewcore, "https");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enablecrossdomain41() {
        try {
            Field webviewclassic_field = WebView.class
                    .getDeclaredField("mProvider");
            webviewclassic_field.setAccessible(true);
            Object webviewclassic = webviewclassic_field.get(this);
            Field webviewcore_field = webviewclassic.getClass()
                    .getDeclaredField("mWebViewCore");
            webviewcore_field.setAccessible(true);
            Object mWebViewCore = webviewcore_field.get(webviewclassic);
            Field nativeclass_field = webviewclassic.getClass()
                    .getDeclaredField("mNativeClass");
            nativeclass_field.setAccessible(true);
            Object mNativeClass = nativeclass_field.get(webviewclassic);

            Method method = mWebViewCore.getClass().getDeclaredMethod(
                    "nativeRegisterURLSchemeAsLocal",
                    new Class[] { int.class, String.class });
            method.setAccessible(true);
            method.invoke(mWebViewCore, mNativeClass, "http");
            method.invoke(mWebViewCore, mNativeClass, "https");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            // 返回上一页面
            webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void setTopBarTitle(String title) {
       if(tvTitle != null){
            tvTitle.setText(title);
       }
    }

    public static void loadUrl(Context mContext, String mUrl) {
        Intent intent = new Intent(mContext, TaoWebviewActivity.class);
        intent.putExtra(AppConstant.KEY_WEB_URL, mUrl);
        mContext.startActivity(intent);
    }
}
