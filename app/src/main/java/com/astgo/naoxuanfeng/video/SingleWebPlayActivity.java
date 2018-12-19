package com.astgo.naoxuanfeng.video;


import android.app.Dialog;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.astgo.naoxuanfeng.R;
import com.astgo.naoxuanfeng.tools.utils.CommonRCAdapter;
import com.astgo.naoxuanfeng.tools.utils.CommonRCViewHolder;
import com.astgo.naoxuanfeng.tools.utils.DialogUtil;
import com.astgo.naoxuanfeng.widget.URLConstant;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.List;

public class SingleWebPlayActivity extends AppCompatActivity {


    private String CurrUrl = "";
    private List<String> urls;
    private RecyclerView recyclerView;
    private WebView webView;
    private FrameLayout fl_back;
    private LinearLayoutManager manager;
    private CommonRCAdapter<String> adapter;
    private TextView tv_title;
    private Dialog dialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_web_play);
        urls = getIntent().getStringArrayListExtra("urls");
        CurrUrl = urls.get(0)+getIntent().getStringExtra("url");
        Log.e("SLH","播放URL："+CurrUrl);
        dialog = DialogUtil.getInstance().showDialog(this, "");
        initView();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){

            //横向


        }else{

            //竖向



        }
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            // destory()
            ViewParent parent = webView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(webView);
            }

            webView.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            webView.getSettings().setJavaScriptEnabled(false);
            webView.clearHistory();
            webView.clearView();
            webView.removeAllViews();

            try {
                webView.destroy();
            } catch (Throwable ex) {

            }
        }
        super.onDestroy();
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        fl_back = (FrameLayout) findViewById(R.id.fl_back);
        fl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView != null) {
                    // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
                    // destory()
                    ViewParent parent = webView.getParent();
                    if (parent != null) {
                        ((ViewGroup) parent).removeView(webView);
                    }

                    webView.stopLoading();
                    // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
                    webView.getSettings().setJavaScriptEnabled(false);
                    webView.clearHistory();
                    webView.clearView();
                    webView.removeAllViews();

                    try {
                        webView.destroy();
                    } catch (Throwable ex) {

                    }
                }
                finish();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        adapter = new CommonRCAdapter<String>(this, urls, R.layout.item_single_text_layout) {
            @Override
            public void convert(CommonRCViewHolder holder, int position, String dataBean) {
                holder.setText(R.id.tv_text, "线路" + (position + 1));
            }
        };
        adapter.setOnRCItemOnClickListener(new CommonRCAdapter.OnRCItemOnClickListener() {
            @Override
            public void setOnRCItemOnClickListener(CommonRCViewHolder holder, int position) {
                webView.stopLoading();
                //ToastUtils.showShortToast(urls.get(position));
                String oldUrl = URLConstant.URL_PLAY;//老解析地址
                URLConstant.URL_PLAY = urls.get(position);
                String newUrl = urls.get(position) + CurrUrl.replace(oldUrl, "");
                CurrUrl = newUrl;
                Log.e("SLH",CurrUrl);
                webView.loadUrl(CurrUrl);

            }
        });
        recyclerView.setAdapter(adapter);

        webView = (WebView) findViewById(R.id.webView);
        WebSettings ws = webView.getSettings();
        ws.setAllowFileAccess(true);
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
        ws.setCacheMode(com.tencent.smtt.sdk.WebSettings.LOAD_DEFAULT);
        // setDefaultZoom  api19被弃用
        // 设置此属性，可任意比例缩放。
        ws.setUseWideViewPort(true);
        // 缩放比例 1
        webView.setInitialScale(1);
        // 告诉WebView启用JavaScript执行。默认的是false。
        ws.setJavaScriptEnabled(true);
        ws.setJavaScriptCanOpenWindowsAutomatically(true);
        ws.setGeolocationEnabled(true);
        //  页面加载好以后，再放开图片
        ws.setBlockNetworkImage(false);

        // 排版适应屏幕
        ws.setLayoutAlgorithm(com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        // WebView是否支持多个窗口。
        ws.setSupportMultipleWindows(true);
        ws.setDomStorageEnabled(true);//这句话必须保留。。否则无法播放优酷视频网页。。其他的可以
        // webview从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            ws.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//        }
        /** 设置字体默认缩放大小(改变网页字体大小,setTextSize  api14被弃用)*/
        ws.setTextSize(com.tencent.smtt.sdk.WebSettings.TextSize.NORMAL);
        ws.setTextZoom(100);
        ws.setUserAgentString("Mozilla/5.0 (Linux; Android 5.1; vivo X6D Build/LMY47I) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/39.0.0.0 Mobile Safari/537.36");
        ws.setPluginState(com.tencent.smtt.sdk.WebSettings.PluginState.ON_DEMAND);
        // webview从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//        }

        /**
         * webView与ViewPager所带来的滑动冲突问题解决方法
         */
//        webView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                webView.getParent().requestDisallowInterceptTouchEvent(true);
//                int x = (int) event.getRawX();
//                int y = (int) event.getRawY();
//                int lastX = 0;
//                int lastY = 0;
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        lastX = x;
//                        lastY = y;
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        int deltaY = y - lastY;
//                        int deltaX = x - lastX;
//                        if (Math.abs(deltaX) < Math.abs(deltaY)) {
//                            webView.getParent().requestDisallowInterceptTouchEvent(false);
//                        } else {
//                            webView.getParent().requestDisallowInterceptTouchEvent(true);
//                        }
//                    default:
//                        break;
//                }
//                return false;
//            }
//        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView webView, String s) {
                tv_title.setText(s);
                super.onReceivedTitle(webView, s);
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                return super.shouldOverrideUrlLoading(webView, s);
            }

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
                if (!dialog.isShowing()) {
                    dialog.show();
                }
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        webView.loadUrl(CurrUrl);


    }
}
