package com.astgo.fenxiao.video;

import android.app.Activity;
import android.view.View;


import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;


/**
 * Created by jingbin on 2016/11/17.
 * - 播放网络视频配置
 * - 上传图片(兼容)
 * 点击空白区域的左边,因是公司图片,自己编辑过,所以显示不全,见谅
 */
public class VipVideoWebChromeClient extends WebChromeClient {



    public VipVideoWebChromeClient(Activity activity) {

    }

    // 播放网络视频时全屏会被调用的方法
    @Override
    public void onShowCustomView(View view, IX5WebChromeClient.CustomViewCallback callback) {
    }

    // 视频播放退出全屏会被调用的
    @Override
    public void onHideCustomView() {
    }


    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
    }

    @Override
    public boolean onJsBeforeUnload(WebView webView, String s, String s1, JsResult jsResult) {
        return super.onJsBeforeUnload(webView, s, s1, jsResult);
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        // 设置title
        this.title = title;
    }

    private String title = "";

    public String getTitle() {
        return title + " ";
    }

}
