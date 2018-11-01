//package com.astgo.fenxiao.video;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.os.Build;
//import android.util.Log;
//
//import com.astgo.fenxiao.widget.URLConstant;
//
//
//import com.blankj.utilcode.utils.ToastUtils;
//import com.tencent.smtt.export.external.interfaces.SslError;
//import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
//import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
//import com.tencent.smtt.sdk.ValueCallback;
//import com.tencent.smtt.sdk.WebView;
//import com.tencent.smtt.sdk.WebViewClient;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class VipVideoWebViewClient extends WebViewClient {
//
//    private WebVideoListActivity mActivity;
//    private List<String> strs;
//
//    public VipVideoWebViewClient(Activity activity, List<String> list) {
//        mActivity = (WebVideoListActivity) activity;
//        strs = list;
//    }
//
//    @Override
//    public WebResourceResponse shouldInterceptRequest(WebView webView, String s) {
//        //Log.e("SLH","shouldInterceptRequest"+s);
//        return super.shouldInterceptRequest(webView, s);
//    }
//
//    @Override
//    public void onReceivedError(WebView webView, int i, String s, String s1) {
//        //Log.e("SLH","onReceivedError:"+s1);
//        super.onReceivedError(webView, i, s, s1);
//    }
//
//    @SuppressWarnings("deprecation")
//    @Override
//    public boolean shouldOverrideUrlLoading(WebView view, String url) {
//
//        //Log.e("SLH", "当前："+view.getUrl());//
//
//        if (CommonUrlUtils.isLetvVideoUrl(url) ||
//                CommonUrlUtils.is1905Movie(url) ||
//                CommonUrlUtils.isMgTV(url) ||
//                CommonUrlUtils.isPPVideoUrl(url)||
//                CommonUrlUtils.isQQVideoUrl(url) ||
//                CommonUrlUtils.isIQiYiVideoUrl(url) ||
//                CommonUrlUtils.isYouKuVideoUrl(url)) {
//            Log.e("SLH", "shouldOverrideUrlLoading ----- :" + url);
//            if (url.startsWith(URLConstant.URL_PLAY)) {
//                String newUrl = url.replace(URLConstant.URL_PLAY,"");
//                Intent intent = new Intent(mActivity, SingleWebPlayActivity.class);
//                intent.putExtra("url", newUrl);
//                intent.putStringArrayListExtra("urls", (ArrayList<String>) strs);
//                mActivity.startActivity(intent);
//            } else {
//          mActivity.loadUrlVideo(url);
////                if (CommonUrlUtils.isMgTV(url)){
////                    view.loadUrl(url);
////                    return true;
////                }else {
////                    mActivity.loadUrlVideo(url);
////                }
////
//
//            }
//
//            return true;
//        }
//
//
//        if (url.contains("m.zchad.top/newzs_3")
//                ||url.contains("g.pwuw.pw/newlg_3")
//                ||url.contains(".apk")
//                ||url.contains("safest.793e.cn")
//                ||url.contains("safest.thli43")
//                ||url.contains("sll.16881616.com")
//                ||url.contains("cn.suyouxuan.com")
//                ){
//            return true;
//        }
//        view.loadUrl(url);
//        return true;
//    }
//
//    @Override
//    public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
//
////        String js4 = "var newscript = document.createElement(\"script\");";
////        js4 += "newscript.src=\"http://js.mgtv.com/imgotv-mobile-default/static/js/app.07e696aeef5bab64eaea.js\";";
////        js4 += "newscript.onload=function(){alert('1');};";  //xxx()代表js中某方法
////        js4 += "document.body.appendChild(newscript);";
////        webView.loadUrl("javascript:"+js4);
//        super.onPageStarted(webView, s, bitmap);
//    }
//
//    @Override
//    public void onPageFinished(WebView view, String url) {
//        super.onPageFinished(view, url);
//        String js = "var newscript = document.createElement(\"script\");";
//        js += "newscript.src=\"http://js.mgtv.com/imgotv-mobile-default/static/js/manifest.ac1fdf33dc07ee21eac0.js\";";
//        //  js += "newscript.onload=function(){alert('1');};";  //xxx()代表js中某方法
//        js += "document.body.appendChild(newscript);";
//        view.loadUrl("javascript:"+js);
//
//        String js2 = "var newscript = document.createElement(\"script\");";
//        js2 += "newscript.src=\"http://js.mgtv.com/imgotv-mobile-default/static/js/vendor.3c3694ac94a1f0aa3302.js\";";
//        //  js2 += "newscript.onload=function(){alert('1');};";  //xxx()代表js中某方法
//        js2 += "document.body.appendChild(newscript);";
//        view.loadUrl("javascript:"+js2);
//        String js3 = "var newscript = document.createElement(\"script\");";
//        js3 += "newscript.src=\"http://js.mgtv.com/imgotv-mobile-default/static/js/app.07e696aeef5bab64eaea.js\";";
//        // js3 += "newscript.onload=function(){alert('1');};";  //xxx()代表js中某方法
//        js3 += "document.body.appendChild(newscript);";
//        view.loadUrl("javascript:"+js3);
//
////        if (CommonUrlUtils.isMgTV(url)){
////           // String js= "$(\".video-area\").innerHTML = '<a href=\"https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png\"><img src=\"https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png\" width=\"100%\" height=230px/></a>';";
////            String js= "$(\".video-area\").remove();";
//////           if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
//////               view.evaluateJavascript("<script>"+js+"</script>",new ValueCallback(){
//////
//////                   @Override
//////                   public void onReceiveValue(Object o) {
//////                       Log.e("SLH","onReceiveValue"+o.toString());
//////                   }
//////               });
//////           }else {
//////               ToastUtils.showShortToast("版本太低");
//////           }
////        view.loadUrl("javascript:"+js);
////        }
//
//    }
//
//
//    //import android.net.http.SslError;
//    //import android.webkit.SslErrorHandler;
//    @Override
//    public void onReceivedSslError(WebView webView,SslErrorHandler sslErrorHandler, SslError sslError) {
//        //handler.cancel(); // Android默认的处理方式
//        sslErrorHandler.proceed();  // 接受所有网站的证书
//
//                       //handleMessage(Message msg); // 进行其他处理
//     //   super.onReceivedSslError(webView, sslErrorHandler, sslError);
//    }
//
//    // 视频全屏播放按返回页面被放大的问题
//    @Override
//    public void onScaleChanged(WebView view, float oldScale, float newScale) {
//        super.onScaleChanged(view, oldScale, newScale);
//        if (newScale - oldScale > 7) {
//            view.setInitialScale((int) (oldScale / newScale * 100)); //异常放大，缩回去。
//        }
//    }
//
//
//    public void showDialog() {
//        // 创建构建器
//        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
//        // 设置参数
//        builder.setTitle("提示")
//                .setMessage("账号已过期")
//                .setPositiveButton("确定", new AlertDialog.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog,
//                                        int which) {
//                        dialog.dismiss();
//                    }
//                });
//        builder.create().show();
//    }
//
//
//}
