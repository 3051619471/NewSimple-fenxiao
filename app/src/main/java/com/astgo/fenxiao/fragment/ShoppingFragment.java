package com.astgo.fenxiao.fragment;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.astgo.fenxiao.R;
import com.astgo.fenxiao.bean.AppConstant;
import com.astgo.fenxiao.tools.NetworkUtil;
import com.astgo.fenxiao.tools.utils.SPUtils;
import com.astgo.fenxiao.webview.IWebPageView;
import com.astgo.fenxiao.webview.Js2Java;
import com.astgo.fenxiao.webview.MyWebChromeClient;
import com.astgo.fenxiao.webview.MyWebViewClient;
import com.astgo.fenxiao.widget.RefreshHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 这是主页fragment，包括了一个自动滚动广告和滚动字幕
 * 还有5个自定义菜单按钮和3个固定菜单按钮
 */
public class ShoppingFragment extends Fragment implements IWebPageView {

    private WebView webView;
    private MyWebChromeClient mWebChromeClient;
    private SmartRefreshLayout refreshLayout;


//    private String url ="http://192.168.0.200/taobaobuy/index.php?m=Home&c=index&a=index";
//    private String url ="http://192.168.0.200/taobaobuy/indexs.php?m=Home";
    private String appendUrl;
    private String shopUrl;
    public ShoppingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shop, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        initData();
        initView(view);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initWebView();
    }

    private void initData() {
//        shopUrl = ((MainActivity)getActivity()).shopUrl;
//        Log.d("czc","MainActivity  shopUrl"+shopUrl);
//        if(TextUtils.isEmpty(shopUrl)){
//            String shopUrlMain = SPUtils.getString(AppConstant.KEY_SHOP_URL_MAIN, "");
//            String shopUrlUserName = SPUtils.getString(AppConstant.KEY_SHOP_URL_USERNAME, "");
//            String shopUrlPwd = SPUtils.getString(AppConstant.KEY_SHOP_URL_PWD,"");
//            String shopUrlKey = SPUtils.getString(AppConstant.KEY_SHOP_URL_KEY, "");
//            shopUrl = shopUrlMain+shopUrlUserName+shopUrlPwd+shopUrlKey;
//            Log.d("czc","shoppingFragment  initData shopUrl"+shopUrl);
//        }
            shopUrl = SPUtils.getString(AppConstant.KEY_SHOP_URL, "");
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    // 初始化 findViewById 和 ClickListener
    private LinearLayout llNodata;
    private void initView(View view) {
        webView = (WebView) view.findViewById(R.id.shop_web_view);
        refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refresh_layout);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setRefreshHeader(new RefreshHeader(this.getActivity().getApplicationContext()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                refreshlayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(NetworkUtil.isConnection(getActivity().getApplicationContext())){
                            webView.setVisibility(View.VISIBLE);
                            llNodata.setVisibility(View.GONE);
                            if(TextUtils.isEmpty(shopUrl)){
                                shopUrl = SPUtils.getString(AppConstant.KEY_SHOP_URL, "");
                            }
                            if(webView != null && !TextUtils.isEmpty(shopUrl)){
                                webView.loadUrl(shopUrl);
                            }
                        }else{
                            webView.setVisibility(View.GONE);
                            llNodata.setVisibility(View.VISIBLE);
                        }
                        refreshlayout.finishRefresh();
                    }
                }, 2000);
            }
        });


        llNodata = (LinearLayout) view.findViewById(R.id.ll_no_data);
        webView.setVisibility(View.VISIBLE);
        llNodata.setVisibility( View.GONE);

    }

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
       // webView.addJavascriptInterface(new Js2Java(this.getActivity()), "js2java");
        webView.setWebViewClient(new MyWebViewClient(this));

//        appendUrl = shopUrl+"&username=18135672078&password=e10adc3949ba59abbe56e057f20f883e&key=c800f47e6a5eb488e7dacd45baea28a0";
        if(NetworkUtil.isConnection(this.getActivity().getApplicationContext())){
            webView.loadUrl(shopUrl);
        }else{
            loadErrorView(true);
        }
    }

    public void loadErrorView(boolean showErrorText) {
        webView.setVisibility(View.GONE);
        llNodata.setVisibility( View.VISIBLE);
        ImageView imError = (ImageView) llNodata.findViewById(R.id.im_error);
        TextView tvError = (TextView) llNodata.findViewById(R.id.tv_error);
        if(showErrorText){
            tvError.setVisibility(View.VISIBLE);
        }else{
            tvError.setVisibility(View.GONE);
        }
        Glide.with(this).load(R.drawable.gif_error).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imError);
    }


    public boolean checkPackage(String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            getActivity().getPackageManager().getApplicationInfo(packageName, PackageManager
                    .GET_UNINSTALLED_PACKAGES);

            return true;
        }
        catch (PackageManager.NameNotFoundException e){
            return false;
        }
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
            llNodata.setVisibility( View.GONE);
        }else{
            loadErrorView(false);
        }

    }

    @Override
    public void addImageClickListener() {

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

}

