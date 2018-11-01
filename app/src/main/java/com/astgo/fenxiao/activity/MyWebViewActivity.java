package com.astgo.fenxiao.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.astgo.fenxiao.R;


public class MyWebViewActivity extends Activity {

    public static final String WEB_TITLE = "web_title";
    public static final String WEB_URL = "web_url";

    private WebView webView;
    private TextView tvNetwork;
    ProgressDialog dialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_web_view);
        webView = (WebView) findViewById(R.id.web_view);
        tvNetwork = (TextView) findViewById(R.id.tv_network);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(dialog != null){
            dialog.dismiss();
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        // WebView展示效果设置

        if (webView != null) {
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    if(dialog != null){
                        dialog.dismiss();
                    }
                }
            });
        }
        webView.getSettings().setUseWideViewPort(true);//设置WebView推荐使用的窗口
        webView.getSettings().setLoadWithOverviewMode(true);//设置WebView加载的页面的模式
        webView.getSettings().setJavaScriptEnabled(true);//允许使用js效果
//        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        String content = getIntent().getStringExtra(WEB_URL);
        if (content != null && URLUtil.isValidUrl(content)) {//判断是否是 url 地址
            Log.e("content1------", content);
            webView.setVisibility(View.VISIBLE);
            tvNetwork.setVisibility(View.GONE);
            webView.loadUrl(content);
            dialog = ProgressDialog.show(this, null, "页面加载中，请稍后..");
            dialog.setCanceledOnTouchOutside(true);
//            webView.reload();
        } else {
            Log.e("content2------", content);
            webView.setVisibility(View.GONE);
            tvNetwork.setVisibility(View.VISIBLE);
        }
    }

}
