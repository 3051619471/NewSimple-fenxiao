package com.astgo.fenxiao.video;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.astgo.fenxiao.BuildConfig;
import com.astgo.fenxiao.MyConstant;
import com.astgo.fenxiao.NetUrl;
import com.astgo.fenxiao.R;
import com.astgo.fenxiao.activity.LoginActivity;
import com.astgo.fenxiao.alipay.AuthResult;
import com.astgo.fenxiao.alipay.PayResult;
import com.astgo.fenxiao.bean.BaseDataObject;
import com.astgo.fenxiao.bean.PayBean;
import com.astgo.fenxiao.tools.FileUtil;
import com.astgo.fenxiao.tools.FileUtils;
import com.astgo.fenxiao.tools.NetworkUtil;
import com.astgo.fenxiao.tools.utils.DebugUtil;
import com.astgo.fenxiao.tools.utils.SPUtils;
import com.astgo.fenxiao.webview.Js2Java;
import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;
import com.lzy.okhttputils.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Response;

public class CommonActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ALBUM = 0x01;
    private static final int REQUEST_CODE_CAMERA = 0x02;
    private static final int REQUEST_CODE_PERMISSION_CAMERA = 0x03;
    public static final int TAKE_PHOTO = 0x04;
    private ValueCallback<Uri> uploadMessage;
    private ValueCallback<Uri[]> uploadMessageAboveL;
    private String mCurrentPhotoPath;
    private String mLastPhothPath;
    private Thread mThread;


    private WebView webView;
    private LinearLayout appBar;
    private ImageView iv_empty;
    public boolean b = false;
    private TextView tvTitle;
    private TextView tv_empty;


    public static final int SDK_PAY_FLAG = 1;
    public static final int SDK_AUTH_FLAG = 2;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TAKE_PHOTO:
                    takePhoto();
                    break;
                case SDK_PAY_FLAG:
                    @SuppressWarnings("unchecked")

                    PayBean payBean = (PayBean) msg.obj;
                    String viewUrl = payBean.viewUrl;
                    PayResult payResult = new PayResult(payBean.map);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus2 = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus2, "9000")) {
                        switchDevice(viewUrl);
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(CommonActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(CommonActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case SDK_AUTH_FLAG:
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(CommonActivity.this,
                                "授权成功" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(CommonActivity.this,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();


                        break;
                    }

            }
            super.handleMessage(msg);
        }

    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_ALBUM || requestCode == REQUEST_CODE_CAMERA) {
            if (uploadMessage == null && uploadMessageAboveL == null) {
                return;
            }

            //取消拍照或者图片选择时
            if (resultCode != RESULT_OK) {
                //一定要返回null,否则<input file> 就是没有反应
                cancelMessage();
            }

            //拍照成功和选取照片时
            if (resultCode == RESULT_OK) {
                Uri imageUri = null;

                switch (requestCode) {
                    case REQUEST_CODE_ALBUM:

                        if (data != null) {

                            imageUri = data.getData();

                        }

                        break;
                    case REQUEST_CODE_CAMERA:

                        if (!TextUtils.isEmpty(mCurrentPhotoPath)) {
                            File file = new File(mCurrentPhotoPath);
                            Uri localUri = Uri.fromFile(file);
                            Intent localIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, localUri);
                            sendBroadcast(localIntent);
                            imageUri = Uri.fromFile(file);

                            mLastPhothPath = mCurrentPhotoPath;
                        }
                        break;
                }


                //上传文件
                if (uploadMessage != null) {
                    uploadMessage.onReceiveValue(imageUri);
                    uploadMessage = null;
                }
                if (uploadMessageAboveL != null) {
                    uploadMessageAboveL.onReceiveValue(new Uri[]{imageUri});
                    uploadMessageAboveL = null;

                }

            }

        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        iv_empty = (ImageView) findViewById(R.id.iv_empty);
        Glide.with(this).load(R.drawable.gif_error).asGif().into(iv_empty);
        tv_empty = (TextView) findViewById(R.id.tv_empty);
        appBar = (LinearLayout) findViewById(R.id.app_bar_videolist);
        ImageView rlBack = (ImageView) findViewById(R.id.iv_app_bar_back);
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gobackHistory();
            }
        });

//        TextView tvCLose = (TextView) findViewById(R.id.app_bar_close);
//        tvCLose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        tv_empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_empty.getText().equals("重新登录")) {
                    startActivity(new Intent(CommonActivity.this, LoginActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    finish();
                }
            }
        });
        tvTitle = (TextView) findViewById(R.id.tv_app_bar_title);

        webView = (WebView) findViewById(R.id.webView);
        WebSettings ws = webView.getSettings();

        ws.setAllowUniversalAccessFromFileURLs(true);
        // 网页内容的宽度是否可大于WebView控件的宽度
        ws.setLoadWithOverviewMode(false);
        // 保存表单数据
        ws.setSaveFormData(false);
        // 是否应该支持使用其屏幕缩放控件和手势缩放
        ws.setSupportZoom(true);
        ws.setBuiltInZoomControls(true);
        ws.setDisplayZoomControls(false);
        // 启动应用缓存
        ws.setAppCacheEnabled(false);
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
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            ws.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//        }
        /** 设置字体默认缩放大小(改变网页字体大小,setTextSize  api14被弃用)*/
        ws.setTextZoom(100);

        webView.setWebChromeClient(new WebChromeClient() {
            //实现定位功能
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }

            @Override
            public void onReceivedTitle(WebView webView, String s) {
                Log.e("SLH", "title" + s);
                tvTitle.setText(s);
                super.onReceivedTitle(webView, s);
            }


            //上传文件选择
            //For Android  >= 5.0
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                uploadMessageAboveL = filePathCallback;
                uploadPicture();
                return true;
            }


            //For Android  >= 4.1
            public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {
                uploadMessage = valueCallback;
                uploadPicture();
            }


        });
        List<String> lists = new ArrayList<>();
        // 与js交互
        webView.addJavascriptInterface(new Js2Java(CommonActivity.this, mHandler, lists), "js2java");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                Log.e("SLH", "onPageStarted" + "URL:" + webView.getUrl());
                iv_empty.setVisibility(View.VISIBLE);
                super.onPageStarted(webView, s, bitmap);
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                Log.e("SLH", "onPageFinished");
                iv_empty.setVisibility(View.GONE);
                super.onPageFinished(webView, s);
            }

            @Override
            public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {

                super.onReceivedError(webView, webResourceRequest, webResourceError);
                Log.e("SLH", "onReceivedError");
            }


            @Override
            public void onReceivedError(WebView webView, int i, String s, String s1) {
                super.onReceivedError(webView, i, s, s1);
                Log.e("SLH", "onReceivedError3");
            }

            @Override
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
                //super.onReceivedSslError(webView, sslErrorHandler, sslError);
                sslErrorHandler.proceed();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                switchDevice(s);
                return true;
            }


        });
        if (NetworkUtil.isConnection(getApplicationContext())) {
//            Map<String,String> map = new HashMap<>();
//            map.put("token", SPUtils.getString(MyConstant.TOKEN,""));
//            webView.loadUrl(getIntent().getStringExtra("url"),map);
//            iv_empty.setVisibility(View.GONE);
//            webView.setVisibility(View.VISIBLE);
            switchDevice(getIntent().getStringExtra("url"));
        } else {
            tv_empty.setVisibility(View.VISIBLE);
            iv_empty.setVisibility(View.GONE);
            webView.setVisibility(View.GONE);
            tv_empty.setText("资源加载失败！");
            //Glide.with(this).load(R.drawable.gif_error).asGif().into(iv_empty);
        }

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

                            if (baseDataObject.getCode() == 100) {
                                tv_empty.setText("重新登录");
                            } else {
                                tv_empty.setText("资源加载失败！");
                            }

                            //Glide.with(CommonActivity.this).load(R.drawable.gif_error).asGif().into(iv_empty);
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ToastUtils.showShortToast(e.getMessage());
                        webView.setVisibility(View.GONE);
                        tv_empty.setVisibility(View.VISIBLE);
                        iv_empty.setVisibility(View.GONE);
                        tv_empty.setText("资源加载失败！");
                        // Glide.with(CommonActivity.this).load(R.drawable.gif_error).asGif().into(iv_empty);

                    }
                });
    }

    @Override
    protected void onDestroy() {
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
            System.gc();
        }
        super.onDestroy();
    }

    private void gobackHistory() {
        if (webView != null && webView.canGoBack()) {
            webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            webView.goBack();
        } else {
            //Intent intent = new Intent();
            setResult(1);
            finish();

        }
    }

    //使用Webview的时候，返回键没有重写的时候会直接关闭程序，这时候其实我们要其执行的知识回退到上一步的操作
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //这是一个监听用的按键的方法，keyCode 监听用户的动作，如果是按了返回键，同时Webview要返回的话，WebView执行回退操作，因为mWebView.canGoBack()返回的是一个Boolean类型，所以我们把它返回为true
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        } else {
            setResult(1);
            finish();
        }
        return true;
    }

    /**
     * 选择相机或者相册
     */
    public void uploadPicture() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择图片上传方式");

        //取消对话框
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                cancelMessage();
            }
        });


        builder.setPositiveButton("相机", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!TextUtils.isEmpty(mLastPhothPath)) {
                    //上一张拍照的图片删除
                    mThread = new Thread(new Runnable() {
                        @Override
                        public void run() {

                            File file = new File(mLastPhothPath);
                            if (file != null) {
                                file.delete();
                            }
                        }
                    });

                    mThread.start();


                }
                takePhoto();

            }
        });
        builder.setNegativeButton("相册", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                webView.clearHistory();
                chooseAlbumPic();


            }
        });

        builder.create().show();

    }

    public void cancelMessage() {
        //一定要返回null,否则<input type='file'>不起做了
        if (uploadMessage != null) {
            uploadMessage.onReceiveValue(null);
            uploadMessage = null;
        }
        if (uploadMessageAboveL != null) {
            uploadMessageAboveL.onReceiveValue(null);
            uploadMessageAboveL = null;

        }
    }

    /**
     * 拍照
     */
    private void takePhoto() {

        StringBuilder fileName = new StringBuilder();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileName.append(UUID.randomUUID()).append("_upload.png");
        File tempFile = new File(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName.toString());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri uri = FileProvider.getUriForFile(this, "com.astgo.fenxiao.provider", tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        } else {
            Uri uri = Uri.fromFile(tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }

        mCurrentPhotoPath = tempFile.getAbsolutePath();
        startActivityForResult(intent, REQUEST_CODE_CAMERA);


    }

    /**
     * 选择相册照片
     */
    private void chooseAlbumPic() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "Image Chooser"), REQUEST_CODE_ALBUM);

    }
}
