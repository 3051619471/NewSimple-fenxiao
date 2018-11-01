package com.astgo.fenxiao.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.astgo.fenxiao.MyConstant;
import com.astgo.fenxiao.NetUrl;
import com.astgo.fenxiao.R;
import com.astgo.fenxiao.activity.LoginActivity;
import com.astgo.fenxiao.bean.BaseDataObject;
import com.astgo.fenxiao.tools.NetworkUtil;
import com.astgo.fenxiao.tools.utils.DebugUtil;
import com.astgo.fenxiao.tools.utils.SPUtils;
import com.astgo.fenxiao.video.CommonActivity;
import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;
import com.lzy.okhttputils.callback.StringCallback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class GearShopFragment extends Fragment {


    private WebView webView;
    private ImageView iv_empty;
    private TextView tv_empty;
    public SmartRefreshLayout refreshLayout;

    public GearShopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_gear_shop, container, false);
        initView(inflate);
        return inflate;
    }

    @Override
    public void onDestroy() {
        if (webView != null) {
            ViewGroup parent = (ViewGroup) webView.getParent();
            if (parent != null) {
                parent.removeView(webView);
            }
            webView.clearCache(true);
            webView.removeAllViews();
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }

    private void initView(View rootView) {
        refreshLayout = (SmartRefreshLayout) rootView.findViewById(R.id.refresh_layout);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadmore(false);
        refreshLayout.setEnableAutoLoadmore(true);
        //refreshLayout.setFooterHeightPx(2);
        //refreshLayout.setRefreshHeader(new RefreshHeader(this.getActivity().getApplicationContext()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                if (NetworkUtil.isConnection(getActivity())) {
                    switchDevice(NetUrl.HOME_SHOP_ONE);
                } else {
                    tv_empty.setVisibility(View.VISIBLE);
                    iv_empty.setVisibility(View.GONE);
                    //Glide.with(getActivity()).load(R.drawable.gif_error).asGif().into(iv_empty);
                    webView.setVisibility(View.GONE);
                    if (refreshLayout != null) refreshLayout.finishRefresh();
                }
            }
        });

        webView = (WebView) rootView.findViewById(R.id.webView);
        iv_empty = (ImageView) rootView.findViewById(R.id.iv_empty);
        Glide.with(getActivity()).load(R.drawable.gif_error).asGif().into(iv_empty);
        tv_empty = (TextView) rootView.findViewById(R.id.tv_empty);
        tv_empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_empty.getText().equals("重新登录")){
                    startActivity(new Intent(getActivity(), LoginActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    getActivity().finish();
                }
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);  //支持js

        //webView.getSettings().setPluginsEnabled(true);  //支持插件

        webView.getSettings().setUseWideViewPort(false);  //将图片调整到适合webview的大小

        webView.getSettings().setSupportZoom(true);  //支持缩放

        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局

        webView.getSettings().supportMultipleWindows();  //多窗口

        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);  //关闭webview中缓存

        webView.getSettings().setAllowFileAccess(true);  //设置可以访问文件

        webView.getSettings().setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点

        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口

        webView.getSettings().setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        webView.getSettings().setBuiltInZoomControls(false); //设置支持缩放

        webView.getSettings().setLoadsImagesAutomatically(true);  //支持自动加载图片
        // webview从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        if (NetworkUtil.isConnection(getActivity())) {
//            Map<String,String> map = new HashMap<>();
//            map.put("token", SPUtils.getString(MyConstant.TOKEN,""));
//            webView.loadUrl(getIntent().getStringExtra("url"),map);
//            iv_empty.setVisibility(View.GONE);
//            webView.setVisibility(View.VISIBLE);
            switchDevice(NetUrl.HOME_SHOP_ONE);
        } else {
            tv_empty.setVisibility(View.VISIBLE);
            iv_empty.setVisibility(View.GONE);
            webView.setVisibility(View.GONE);
            // Glide.with(getActivity()).load(R.drawable.gif_error).asGif().into(iv_empty);
        }
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e("SLH", url);
                Intent intent = new Intent(getActivity(), CommonActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                iv_empty.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                iv_empty.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });

        /**
         * webView与ViewPager所带来的滑动冲突问题解决方法
         */
        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                webView.getParent().requestDisallowInterceptTouchEvent(true);
                int x = (int) event.getRawX();
                int y = (int) event.getRawY();
                int lastX = 0;
                int lastY = 0;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = x;
                        lastY = y;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int deltaY = y - lastY;
                        int deltaX = x - lastX;
                        if (Math.abs(deltaX) < Math.abs(deltaY)) {
                            webView.getParent().requestDisallowInterceptTouchEvent(false);
                        } else {
                            webView.getParent().requestDisallowInterceptTouchEvent(true);
                        }
                    default:
                        break;
                }
                return false;
            }
        });


    }

    public void switchDevice(final String url) {

        OkHttpUtils.get(NetUrl.SWITCH_DEVICE)     // 请求方式和请求url
                .tag("switchDevice")// 请求的 tag, 主要用于取消对应的请求
                .headers("token", SPUtils.getString(MyConstant.TOKEN, ""))
                .cacheKey("switchDevice")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        DebugUtil.error("switchDevice" + s);
                        //{"data":[{"title":"\u6807\u98981","imgurl":"http:\/\/hssq.dykj168.com\/Uploads\/carouselimg\/5a0d48da3b4bf.png","jumpurl":"http:\/\/www.baidu.com"},{"title":"\u6863\u4f4d\u5546\u54c1","imgurl":"http:\/\/hssq.dykj168.com\/Uploads\/carouselimg\/5ac3439b29f58.png","jumpurl":"\/home\/index\/lvgoods"},{"title":"\u6807\u98983","imgurl":"http:\/\/hssq.dykj168.com\/Uploads\/carouselimg\/5a0d48a028e85.png","jumpurl":"http:\/\/www.baidu.com"},{"title":"\u6807\u98982","imgurl":"http:\/\/hssq.dykj168.com\/Uploads\/carouselimg\/5a0d48fd48b9c.png","jumpurl":"www.baidu.com"},{"title":"\u6807\u98984","imgurl":"http:\/\/hssq.dykj168.com\/Uploads\/carouselimg\/5a0d491c077b1.png","jumpurl":"www.baidu.com"}],"info":"success","status":1}
                        BaseDataObject baseDataObject = (BaseDataObject) BaseDataObject.getBaseBean(s, BaseDataObject.class);
                        if (baseDataObject.getCode() == 1) {
                            Map<String, String> map = new HashMap<>();
                            map.put("token", SPUtils.getString(MyConstant.TOKEN, ""));
                            webView.loadUrl(url, map);
                            webView.setVisibility(View.VISIBLE);
                            tv_empty.setVisibility(View.GONE);
                        } else {
                            ToastUtils.showShortToast(baseDataObject.getMsg());
                            webView.setVisibility(View.GONE);
                            tv_empty.setVisibility(View.VISIBLE);
                            iv_empty.setVisibility(View.GONE);
                            if (baseDataObject.getCode() == 100){
                                tv_empty.setText("重新登录");
                            }else {
                                tv_empty.setText("资源加载失败！");
                            }
                            // Glide.with(getActivity()).load(R.drawable.gif_error).asGif().into(iv_empty);
                        }
                        if (refreshLayout != null) {
                            refreshLayout.finishRefresh();
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ToastUtils.showShortToast(e.getMessage());
                        webView.setVisibility(View.GONE);
                        tv_empty.setVisibility(View.VISIBLE);
                        iv_empty.setVisibility(View.GONE);
                        //Glide.with(getActivity()).load(R.drawable.gif_error).asGif().into(iv_empty);
                        if (refreshLayout != null) {
                            refreshLayout.finishRefresh();
                        }
                    }
                });
    }
}
