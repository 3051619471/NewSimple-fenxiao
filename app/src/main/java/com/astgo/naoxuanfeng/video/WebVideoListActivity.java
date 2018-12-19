package com.astgo.naoxuanfeng.video;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.astgo.naoxuanfeng.MyConstant;
import com.astgo.naoxuanfeng.NetUrl;
import com.astgo.naoxuanfeng.R;
import com.astgo.naoxuanfeng.bean.BaseDataObject;
import com.astgo.naoxuanfeng.bean.PlatformUrl;
import com.astgo.naoxuanfeng.tools.utils.DebugUtil;
import com.astgo.naoxuanfeng.tools.utils.DialogUtil;
import com.astgo.naoxuanfeng.tools.utils.SPUtils;
import com.astgo.naoxuanfeng.webview.Js2Java;
import com.astgo.naoxuanfeng.widget.URLConstant;
import com.blankj.utilcode.utils.ToastUtils;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;
import com.lzy.okhttputils.callback.StringCallback;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;


/**
 * Created by ast009 on 2018/1/23.
 */

public class WebVideoListActivity extends AppCompatActivity {
    private String videoListUrl = "";
    private LinearLayout appBar;
    private FrameLayout mFrameLayout;

    public WebView mainWebview;
    private Dialog mDialog = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.activity_video_list);
        mDialog = DialogUtil.getInstance().showDialog(this,"");
        getIntentData();
        initview();
    }

    private void getUrlByPlatform() {

        int platform = getIntent().getIntExtra("platform", 0);
        if (platform == 999) {
           // mWebChromeClient = new VipVideoWebChromeClient(WebVideoListActivity.this);
            mainWebview.setWebChromeClient(new WebChromeClient());
            List<String> str = new ArrayList<>();
            mainWebview.setWebViewClient(new MyWebViewClient(WebVideoListActivity.this, str));
            mainWebview.addJavascriptInterface(new Js2Java(WebVideoListActivity.this, new Handler(), str), "js2java");
            mainWebview.loadUrl(videoListUrl);
            return;
        }
//        token
//        system_type		客户端类型：android、ios
//        type			视频平台类型：1：腾讯视频-手机版链接、2：爱奇艺、3：优酷、4：芒果TV、5：乐视、6：PPTV、7：搜狐视频、8：看看视频、9：暴风影音、10：1905
        OkHttpUtils.post(NetUrl.VIDEO_analysis_ADDRESSS)     // 请求方式和请求url
                .tag("getUrlByPlatform")// 请求的 tag, 主要用于取消对应的请求
                .headers("token", SPUtils.getString(MyConstant.TOKEN, ""))
                .params("system_type", "android")
                .params("type", platform)
                .cacheKey("getUrlByPlatform")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        DebugUtil.error("getUrlByPlatform" + s);
                        BaseDataObject baseDataObject = (BaseDataObject) BaseDataObject.getBaseBean(s, BaseDataObject.class);
                        if (baseDataObject.getCode() == 1) {
                            if (!TextUtils.isEmpty(baseDataObject.getData().toString())) {
                                PlatformUrl platformUrl = (PlatformUrl) BaseDataObject.getBaseBean(s, PlatformUrl.class);
                                URLConstant.URL_PLAY = platformUrl.getData().getApilist().get(0);
                                mainWebview.setWebChromeClient(new WebChromeClient(){
                                    @Override
                                    public void onProgressChanged(WebView webView, int i) {
                                        if (i!=100){
                                            mDialog.show();
                                        }else {
                                            mDialog.dismiss();
                                        }
                                        super.onProgressChanged(webView, i);
                                    }
                                });
                                mainWebview.setWebViewClient(new MyWebViewClient(WebVideoListActivity.this, platformUrl.getData().getApilist()));
                                mainWebview.addJavascriptInterface(new Js2Java(WebVideoListActivity.this, new Handler(), platformUrl.getData().getApilist()), "js2java");
                                mainWebview.loadUrl(videoListUrl);
                            }
                        } else {
                            ToastUtils.showShortToast(baseDataObject.getMsg());
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ToastUtils.showShortToast(e.getMessage());
                    }
                });


    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Constant.INTENT_KEY_WEB_URL)) {
            videoListUrl = intent.getStringExtra(Constant.INTENT_KEY_WEB_URL);
            Log.e("SLH", "加载url：" + videoListUrl);
        }

    }


    private void initview() {
        initAppBar();
        mainWebview = (WebView) findViewById(R.id.main_webview);
        initWebview();
        getUrlByPlatform();
    }

    private void initAppBar() {

        appBar = (LinearLayout) findViewById(R.id.app_bar_videolist);
        RelativeLayout rlBack = (RelativeLayout) findViewById(R.id.rl_title_back);
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gobackHistory();
            }
        });

        TextView tvCLose = (TextView) findViewById(R.id.app_bar_close);
        tvCLose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView tvTitle = (TextView) findViewById(R.id.tv_app_bar_title);
        tvTitle.setText("VIP视频");
    }


    private void initWebview() {
        mFrameLayout = (FrameLayout) findViewById(R.id.fg_content);

        WebSettings ws = mainWebview.getSettings();

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
        ws.setCacheMode(android.webkit.WebSettings.LOAD_DEFAULT);
        // setDefaultZoom  api19被弃用
        // 设置此属性，可任意比例缩放。
        ws.setUseWideViewPort(true);
        // 缩放比例 1
        mainWebview.setInitialScale(1);
        // 告诉WebView启用JavaScript执行。默认的是false。
        ws.setJavaScriptEnabled(true);
        ws.setJavaScriptCanOpenWindowsAutomatically(true);
        ws.setGeolocationEnabled(true);
        //  页面加载好以后，再放开图片
        ws.setBlockNetworkImage(false);

        // 排版适应屏幕
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        // WebView是否支持多个窗口。
        ws.setSupportMultipleWindows(true);
        ws.setDomStorageEnabled(true);//这句话必须保留。。否则无法播放优酷视频网页。。其他的可以
        // webview从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            ws.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//        }
        /** 设置字体默认缩放大小(改变网页字体大小,setTextSize  api14被弃用)*/
        ws.setTextSize(WebSettings.TextSize.NORMAL);
        ws.setTextZoom(100);
        ws.setUserAgentString("Mozilla/5.0 (Linux; Android 5.1; vivo X6D Build/LMY47I) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/39.0.0.0 Mobile Safari/537.36");
        ws.setPluginState(WebSettings.PluginState.ON_DEMAND);


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 返回上一页面
            gobackHistory();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void gobackHistory() {
        if (mainWebview != null && mainWebview.canGoBack()) {
            mainWebview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            mainWebview.goBack();
        } else {
            finish();
        }
    }


    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mainWebview != null) {
            ViewGroup parent = (ViewGroup) mainWebview.getParent();
            if (parent != null) {
                parent.removeView(mainWebview);
            }
            mainWebview.removeAllViews();
            mainWebview.loadUrl("about:blank");
            mainWebview.stopLoading();
            mainWebview.setWebChromeClient(null);
            mainWebview.setWebViewClient(null);
            mainWebview.destroy();
            mainWebview = null;
            System.gc();
        }
    }
    public String OperationHtml(String js) {

        StringBuilder builder = new StringBuilder();
        // add javascript prefix
        builder.append("javascript:(function() { ");
        builder.append(js);
        builder.append("})()");
        return builder.toString();
    }
    class MyWebViewClient extends WebViewClient {
        private WebVideoListActivity mActivity;
        private List<String> strs;

        public MyWebViewClient(Activity activity, List<String> list) {
            mActivity = (WebVideoListActivity) activity;
            strs = list;
        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            String js = null;
            if (CommonUrlUtils.isQQVideoUrl(url)) {//mod_head_vip
                js = "if(document.getElementsByClassName('tvp_app_btn').length!=0&&document.getElementsByClassName('site_header U_bg_a none').length!=0){" +
                        "document.getElementsByClassName('tvp_app_btn')[0].remove();" +
                        "document.getElementsByClassName('btn_user_text U_color_b')[0].innerHTML = \"\";" +
                        "document.getElementsByClassName('btn_search')[0].innerHTML = \"\";" +
                        "document.getElementsByClassName('btn_menu')[0].innerHTML = \"\";" +
                        "document.getElementsByClassName('site_player')[0].innerHTML= \"<a href = '" + URLConstant.URL_PLAY + mainWebview.getUrl() + "'><img src = '" + URLConstant.PLAY_IMG + "' width = 100% height = 100%></a>\";}"
                        +//;
                        "document.getElementsByClassName('mod_head_vip')[0].remove();" +
                        "document.getElementsByClassName('player_viptips _tips')[0].innerHTML = \"<a href = '" + URLConstant.URL_PLAY + mainWebview.getUrl() + "'><img src = '" + URLConstant.PLAY_IMG + "' width = 100% height = 100%></a>\"";


            } else if (CommonUrlUtils.isMgTV(url)) {//ht
                js = "document.getElementsByClassName('video-area')[0].innerHTML = \"<a href = '" +  URLConstant.URL_PLAY + mainWebview.getUrl() + "'><img src = '" + URLConstant.PLAY_IMG + "' width = 100% height = 100%></a>\";" +
                        "document.getElementsByClassName('ht')[0].innerHTML = \"\";" +
                        "document.getElementsByClassName('ad-banner-t10 ad-banner')[0].remove();";


            } else if (CommonUrlUtils.isIQiYiVideoUrl(url)) {//m-box m-box-top m-user m-box-items m-box-items-Ptop m-box-items-Pbottom


                js = "if(document.getElementsByClassName('m-box m-box-top').length != 0){document.getElementsByClassName('m-box m-box-top')[0].innerHTML = \"\";\n" +
                        "document.getElementsByClassName('m-box m-box-top')[0].innerHTML = \"<a href = '" +  URLConstant.URL_PLAY + mainWebview.getUrl() + "'><img src = '" + URLConstant.PLAY_IMG + "' width = 100% height = 100%></a>\";" +
                        "}" +
                        //电影覆盖 m-box-items m-box-items-full
                        "document.getElementsByClassName('m-header-group')[0].remove();" +
                        "document.getElementsByClassName('m-box-items-Ptop m-box-items-Pbottom')[0].remove();" +
                        "document.getElementsByClassName('m-box-connect')[0].innerHTML = \"<a href = '" +  URLConstant.URL_PLAY + mainWebview.getUrl() + "'><img src = '" + URLConstant.PLAY_IMG + "' width = 100% height = 100%></a>\";";


            } else if (CommonUrlUtils.isLetvVideoUrl(url)) {//column playB headBox

                js = "document.getElementsByTagName('header')[0].remove();" +//playCard column leappMore1 c1526169233739
                        "document.getElementsByClassName('playCard column')[0].remove();" +//arkBox
                        "document.getElementsByClassName('arkBox')[0].remove();" +
                        //  "document.getElementsByClassName('column playB')[0].innerHTML = \"<a href = '" + AppConstant.analysis_address + mWebView.getUrl() + "'><img src = '" + URLConstant.PLAY_IMG + "' width = 100% height = 100%></a>\";" +
                        "document.getElementsByClassName('column playB')[0].innerHTML = \"<a href = '" + URLConstant.URL_PLAY + mainWebview.getUrl()+ "'><img src = '" + URLConstant.PLAY_IMG + "' width = 100% height = 100%></a>\"";

            } else if (CommonUrlUtils.isPPVideoUrl(url)) { //m_header /player-info openapp-bg
                js = "document.getElementsByClassName('m_user_headpic')[0].remove();" +
                        "document.getElementsByClassName('player-info openapp-bg')[0].remove();" +
                        "document.getElementsByClassName('search cf')[0].remove();" +
                        "document.getElementsByClassName('afpPosition')[0].remove();" +
                        "document.getElementsByClassName('playbox')[0].innerHTML = \"<a href = '" +  URLConstant.URL_PLAY + mainWebview.getUrl() + "'><img src = '" + URLConstant.PLAY_IMG + "' width = 100% height = 100%></a>\";";

            } else if (CommonUrlUtils.isYouKuVideoUrl(url)) {//player-box x_header brief-btn-wrap
                js = "document.getElementsByClassName('x_login_icon')[0].remove();" +
                        "document.getElementsByClassName('x_search_icon')[0].remove();" +
                        "document.getElementsByClassName('x_menu_icon')[0].remove();" +
                        "document.getElementsByClassName('vo-links')[0].remove();" +//vo-links m-box-items
                        "document.getElementsByClassName('border-bottom clearfix')[0].remove();" +
                        "document.getElementsByClassName('player-box')[0].innerHTML = \"<a href = '" +  URLConstant.URL_PLAY + mainWebview.getUrl() + "'><img src = '" + URLConstant.PLAY_IMG + "' width = 100% height = 100%></a>\";";

            }
//            else if (CommonUrlUtils.isBaoFeng(url)) {//hd-play-box
//                //需要处理分集
//                js = "document.getElementsByTagName('header')[0].remove();" +
//                        "document.getElementsByClassName('hd-play-box')[0].innerHTML = \"<a href = '" +  URLConstant.URL_PLAY + mainWebview.getUrl() + "'><img src = '" + URLConstant.PLAY_IMG + "' width = 100% height = 100%></a>\";";
//
//            }
            view.loadUrl(OperationHtml(js));


        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.e("slh",url);
            String currUrl = view.getUrl();
            if (url.startsWith(URLConstant.URL_PLAY)) {
                Intent intent = new Intent(WebVideoListActivity.this, SingleWebPlayActivity.class);
                intent.putStringArrayListExtra("urls", (ArrayList<String>) strs);
                intent.putExtra("url", currUrl);
                startActivity(intent);
            } else {
                if (url.contains("/m.zchad.top/newzs_3")
                        || url.contains("g.pwuw.pw/newzs_3")
                        || url.contains(".apk")
                        || url.contains("sll.16881616.com")
                        || url.contains("m.afjam.top/newlg_3/")
                        || url.contains("safest.793e.cn")
                        || url.contains("/m.afjam.top")
                        || url.contains("m.taobao.com")
                        || url.contains("letvclient://msiteAction")
                        || url.contains("//mobile/home")
                        || url.contains("qqnews")
                        || url.contains("qnreading://")
                        || url.contains("tbopen://m.taobao.com")
                        || url.contains("hntvmobile")
                        || url.contains("youku://play?spm=")
                        || url.contains("//page/player/halfscreen?")
                        || url.contains("safest.thli43.cn")
                        ) {

                } else {
                    view.loadUrl(url);
                }


            }


            return true;
        }

        @Override
        public void onReceivedSslError(WebView webView, SslErrorHandler
                sslErrorHandler, SslError
                                               sslError) {
            sslErrorHandler.proceed();
        }

        /**
         * 参数说明：
         *
         * @param view 接收WebViewClient的那个实例，前面看到webView.setWebViewClient(new MyAndroidWebViewClient())，即是这个webview。
         * @param url  即将加载的url 资源
         */
        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);

        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String
                failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            //DebugUtils.Loge("onReceivedError:" + failingUrl);

        }
    }


}
