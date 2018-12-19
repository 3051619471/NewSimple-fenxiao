package com.astgo.naoxuanfeng.webview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.text.TextUtils;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.astgo.naoxuanfeng.bean.AppConstant;
import com.astgo.naoxuanfeng.fragment.ShoppingFragment;
import com.astgo.naoxuanfeng.tools.utils.SPUtils;
import com.astgo.naoxuanfeng.webview.newwebview.TaoWebviewActivity;


/**
 * Created by jingbin on 2016/11/17.
 * 监听网页链接:
 * - 根据标识:打电话、发短信、发邮件
 * - 进度条的显示
 * - 添加javascript监听
 */
public class MyWebViewClient extends WebViewClient {

    private IWebPageView mIWebPageView;
    private ShoppingFragment mFragment;

    private TaoWebviewActivity taoWebviewActivity;
    public MyWebViewClient(IWebPageView mIWebPageView) {
        this.mIWebPageView = mIWebPageView;
        if(mIWebPageView instanceof ShoppingFragment){
            mFragment = (ShoppingFragment) mIWebPageView;
        }

        if(mIWebPageView instanceof TaoWebviewActivity){
            taoWebviewActivity = (TaoWebviewActivity)mIWebPageView;
        }

    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        String appenUrl="";

        if(url.contains("?")){
            appenUrl = url+ SPUtils.getString(AppConstant.KEY_SHOP_URL_SUFFIX,"");
        }else{
            appenUrl = url+"?"+SPUtils.getString(AppConstant.KEY_SHOP_URL_SUFFIX,"");
        }
        if(!TextUtils.isEmpty(appenUrl)){
            if(mFragment != null){
                Intent intent = new Intent(mFragment.getActivity(), TaoWebviewActivity.class);
                intent.putExtra(AppConstant.KEY_WEB_URL,appenUrl);
                mFragment.startActivity(intent);
                return true;
            }

            if(taoWebviewActivity !=  null){
                Intent intent = new Intent(taoWebviewActivity, TaoWebviewActivity.class);
                intent.putExtra(AppConstant.KEY_WEB_URL,appenUrl);
                taoWebviewActivity.startActivity(intent);
                return true;
            }
        }

        mIWebPageView.startProgress();

        return false;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }



    @Override
    public void onPageFinished(WebView view, String url) {
        mIWebPageView.addImageClickListener();
        super.onPageFinished(view, url);
        String title = view.getTitle();
        if("系统发生错误".equals(title)){
            if(mFragment != null){
                mFragment.loadErrorView(true);
            }
            if(taoWebviewActivity != null){
                taoWebviewActivity.loadErrorView(true);
            }
            return;
        }
        if (!TextUtils.isEmpty(title)) {
            if(taoWebviewActivity != null){
                taoWebviewActivity.setTopBarTitle(title);
            }
        }
    }


    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            int errorCode = error.getErrorCode();
            if(errorCode == -2 || errorCode == -8){
                if(mFragment != null){
                    mFragment.loadErrorView(true);
                }
                if(taoWebviewActivity != null){
                    taoWebviewActivity.loadErrorView(true);
                }
            }
        }
    }

    @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed();
    }


    // 视频全屏播放按返回页面被放大的问题
    @Override
    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        super.onScaleChanged(view, oldScale, newScale);
        if (newScale - oldScale > 7) {
            view.setInitialScale((int) (oldScale / newScale * 100)); //异常放大，缩回去。
        }
    }
}
