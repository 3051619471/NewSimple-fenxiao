package com.astgo.naoxuanfeng.webview;

/**
 * Created by jingbin on 2016/11/17.
 */
public interface IWebPageView {


    // 显示webview
    void showWebView();

    // 隐藏webview
    void hindWebView();


    //  进度条先加载到90%,然后再加载到100%
    void startProgress();

    /**
     * 进度条变化时调用
     */
    void progressChanged(int newProgress);

    /**
     * 添加js监听
     */
    void addImageClickListener();



}
