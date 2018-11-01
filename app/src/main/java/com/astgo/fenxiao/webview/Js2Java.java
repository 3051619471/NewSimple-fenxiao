package com.astgo.fenxiao.webview;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.astgo.fenxiao.NetUrl;
import com.astgo.fenxiao.R;
import com.astgo.fenxiao.activity.LoginActivity;
import com.astgo.fenxiao.alipay.OrderInfoUtil2_0;
import com.astgo.fenxiao.bean.AliPayBean;
import com.astgo.fenxiao.bean.AppConstant;
import com.astgo.fenxiao.bean.BaseDataObject;
import com.astgo.fenxiao.bean.PayBean;
import com.astgo.fenxiao.bean.UpdateBean;
import com.astgo.fenxiao.bean.WXPay;
import com.astgo.fenxiao.bean.WebViewJsBean;
import com.astgo.fenxiao.fragment.HomeFragment;
import com.astgo.fenxiao.tools.utils.DebugUtil;
import com.astgo.fenxiao.tools.utils.DialogUtil;
import com.astgo.fenxiao.tools.utils.SPUtils;
import com.astgo.fenxiao.tools.utils.ToastUtil;
import com.astgo.fenxiao.update.UpdateAppUtils;
import com.astgo.fenxiao.video.CommonActivity;
import com.astgo.fenxiao.video.CommonUrlUtils;
import com.astgo.fenxiao.video.SingleWebPlayActivity;
import com.astgo.fenxiao.video.WebVideoListActivity;
import com.astgo.fenxiao.webview.newwebview.TaoWebviewActivity;
import com.astgo.fenxiao.widget.URLConstant;
import com.astgo.fenxiao.zxing.activity.CaptureActivity;
import com.blankj.utilcode.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;
import com.lzy.okhttputils.callback.FileCallback;
import com.lzy.okhttputils.callback.StringCallback;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import okhttp3.Call;
import okhttp3.Response;

public class Js2Java implements PlatformActionListener {

    private Activity activity;
    private Handler mHandler;
    private Context mContext;
    private List<String> strs;

    public Js2Java(Activity activity, Handler handler, List<String> urls) {
        this.activity = activity;
        mContext = activity.getApplicationContext();
        mHandler = handler;
        strs = urls;
    }

    @JavascriptInterface
    public void scanqr() {
        Intent intent = new Intent(activity,
                CaptureActivity.class);
        activity.startActivityForResult(intent, CaptureActivity.REQUEST_CODE_SCAN);

    }

    @JavascriptInterface
    public void juji(String url) {
        // ToastUtils.showShortToast(url);
        if (CommonUrlUtils.isYouKuVideoUrl(url)) {//优酷分集
            if (activity instanceof WebVideoListActivity) {
                String currUrl = ((WebVideoListActivity) activity).mainWebview.getUrl();
                Log.e("SLH", "youku:" + currUrl);
                Intent intent = new Intent(activity, SingleWebPlayActivity.class);
                intent.putExtra("url", currUrl);
                intent.putStringArrayListExtra("urls", (ArrayList<String>) strs);
                activity.startActivity(intent);
            }

        }
        if (url.startsWith("/")) {//爱奇艺分集
            Log.e("SLH", "点击分集的URL:" + url);
            if (url.startsWith("//")) {
                url = "http:" + url;
                Intent intent = new Intent(activity, SingleWebPlayActivity.class);
                intent.putExtra("url", url);
                intent.putStringArrayListExtra("urls", (ArrayList<String>) strs);
                activity.startActivity(intent);
            } else if (url.startsWith("/")) {
                url = "http://m.iqiyi.com" + url;
                Intent intent = new Intent(activity, SingleWebPlayActivity.class);
                intent.putExtra("url", url);
                intent.putStringArrayListExtra("urls", (ArrayList<String>) strs);
                activity.startActivity(intent);
            }

        }
        if (CommonUrlUtils.isLetvVideoUrl(url)) {
            if (activity instanceof WebVideoListActivity) {
                String currUrl = ((WebVideoListActivity) activity).mainWebview.getUrl();
                Log.e("SLH", "letv:" + currUrl);
                Intent intent = new Intent(activity, SingleWebPlayActivity.class);
                intent.putExtra("url", currUrl);
                intent.putStringArrayListExtra("urls", (ArrayList<String>) strs);
                activity.startActivity(intent);
            }

        }
    }

    @JavascriptInterface
    public void copyLink(String content) {
        ClipboardManager clip = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
//                clip.getText(); // 粘贴
        clip.setText(content); // 复制
        ToastUtils.showShortToast("复制成功");
    }

    @JavascriptInterface
    public void quiteLogin() {
        SPUtils.clear();
        activity.startActivity(new Intent(activity, LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
        activity.finish();

    }

    @JavascriptInterface
    public void update() {
        final Dialog dialog = DialogUtil.getInstance().showDialog(activity, "");
        OkHttpUtils.get(NetUrl.GET_VERSION_INFO)     // 请求方式和请求url
                .tag("update")// 请求的 tag, 主要用于取消对应的请求
                //.headers("token", SPUtils.getString(MyConstant.TOKEN, ""))
                .cacheKey("update")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        DebugUtil.error("update" + s);
                        BaseDataObject baseDataObject = (BaseDataObject) BaseDataObject.getBaseBean(s, BaseDataObject.class);
                        if (baseDataObject.getCode() == 1) {
                            if (!TextUtils.isEmpty(baseDataObject.getData().toString())) {
                                UpdateBean updateBean = (UpdateBean) BaseDataObject.getBaseBean(s, UpdateBean.class);
                                if (updateBean != null) {
                                    UpdateAppUtils.from(activity)
                                            .checkBy(UpdateAppUtils.CHECK_BY_VERSION_NAME) //更新检测方式，默认为VersionCode
                                            //.serverVersionCode(2)
                                            .serverVersionName(updateBean.getData().getVersion_number())
                                            .apkPath(updateBean.getData().getDownloadfile())
                                            .downloadBy(UpdateAppUtils.DOWNLOAD_BY_APP) //下载方式：app下载、手机浏览器下载。默认app下载
                                            .isForce(false) //是否强制更新，默认false 强制更新情况下用户不同意更新则不能使用app
                                            .needFitAndroidN(false)
                                            .update();
                                }
                            }
                        } else {
                            ToastUtils.showShortToast(baseDataObject.getMsg());
                        }
                        dialog.dismiss();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        dialog.dismiss();
                        ToastUtils.showShortToast(e.getMessage());
                    }
                });


    }

    @JavascriptInterface
    public void openTaoBaoToBuy(String jsJson) {
        WebViewJsBean webViewJsBean = new Gson().fromJson(jsJson, new TypeToken<WebViewJsBean>() {
        }.getType());
        if (webViewJsBean != null) {
            int status = webViewJsBean.getStatus();
            if (status == 1) {
                String url = webViewJsBean.getUrl();

                if (!TextUtils.isEmpty(url)) {
                    if (url.startsWith("http://")) {
                        Pattern p = Pattern.compile("http");
                        Matcher m = p.matcher(url);
                        String tmp = m.replaceFirst("taobao");
                        jump2Taobao(tmp);
                    }
                    if (url.startsWith("https://")) {
                        Pattern p = Pattern.compile("https");
                        Matcher m = p.matcher(url);
                        String tmp = m.replaceFirst("taobao");
                        jump2Taobao(tmp);
                    }
                }
            }
        }
    }

    private void jump2Taobao(String url) {
        Log.d("xxxczc", "taobaoUrl:" + url);
        if (checkPackage("com.taobao.taobao")) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri uri = Uri.parse(url);
            intent.setData(uri);
            activity.startActivity(intent);
//            activity.finish();
        } else {
            ToastUtils.showShortToast("请先安装淘宝");
        }
    }


    public boolean checkPackage(String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            mContext.getPackageManager().getApplicationInfo(packageName, PackageManager
                    .GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    //    //调起支付
    @JavascriptInterface
    public void applyPay(String json) {
        Log.e("SLH", "支付：" + json);

        try {
            JSONObject job = new JSONObject(json);
            if (job.has("type")) {
                String type = job.getString("type");
                if ("alipay".equals(type)) { //调起阿里支付
                    AliPayBean aliPayBean = new Gson().fromJson(json, AliPayBean.class);
                    alipay(aliPayBean.getAppid(), aliPayBean.getPrivateKey()
                            , aliPayBean.getPrice(), aliPayBean.getTitle(), aliPayBean.getTitle(), aliPayBean.getOrderId(), aliPayBean.getNotify_url(), aliPayBean.getViewurl());
                } else if ("wechat".equals(type)) {//微信支付
                    WXPay wxPay = new Gson().fromJson(json, WXPay.class);
                    wxPay(wxPay);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * /微信支付
     */
    private void wxPay(WXPay wxPay) {
        //{"appid":"wx4eeaa909776fb55a","mch_id":"1503083041","nonce_str":"YUCKp2uPkuRXc4i1","prepay_id":"wx08142816301372661f1995ae3598675191","sign":"968634B6A4101534935E82620D6E6738","timestamp":"1525760896","key":"heshunshangquanweixinpay20180508","package":"Sign=WXPay","type":"wechat","viewurl":"http://hssq.dykj168.com/index.php?m=Home&c=order&a=view&order_id=348"}
        if (activity != null) {
            if (!checkPackage("com.tencent.mm")) {
                ToastUtils.showShortToast("请先安装微信");
                return;
            }
            SPUtils.putString(AppConstant.KEY_VIEW_URL, wxPay.getViewurl());
            AppConstant.WX_APP_ID = wxPay.getAppid();//将appid写到常量中保存
            IWXAPI api = WXAPIFactory.createWXAPI(activity, AppConstant.WX_APP_ID);
            // api.registerApp(AppConstant.WX_APP_ID);
            PayReq req = new PayReq();
            req.appId = wxPay.getAppid();//appid号
            req.partnerId = wxPay.getMch_id();//商户号
            req.prepayId = wxPay.getPrepay_id();//
            req.nonceStr = wxPay.getNonce_str();
            req.timeStamp = wxPay.getTimestamp();
            req.packageValue = wxPay.getPackageX();
            req.sign = wxPay.getSign();
            api.sendReq(req);
        }
    }

    /**
     * 支付宝支付业务
     *
     * @param APPID
     * @param RSA2_PRIVATE
     */
    public void alipay(String APPID, String RSA2_PRIVATE, String total_amount, String subject, String body, String orderno, String notify_url, final String viewUrl) {
        if (TextUtils.isEmpty(APPID) || TextUtils.isEmpty(RSA2_PRIVATE)) {
            new AlertDialog.Builder(activity).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                            dialoginterface.dismiss();
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);

        /**
         * String app_id, boolean rsa2,String total_amount,String subject,String body,String orderno
         */
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2, total_amount, subject, body, orderno, notify_url);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        String sign = OrderInfoUtil2_0.getSign(params, RSA2_PRIVATE, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.e("SLH", result.toString());
                PayBean payBean = new PayBean();
                payBean.map = result;
                payBean.viewUrl = viewUrl;

                Message msg = new Message();
                msg.what = CommonActivity.SDK_PAY_FLAG;
                msg.obj = payBean;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @JavascriptInterface
    public void backPage() {
        if (activity != null && activity instanceof TaoWebviewActivity) {
            activity.finish();
        }
    }

    public void WXShare(String json) {
        Log.e("SLH", "share：" + json);
        try {
            if (!checkPackage("com.tencent.mm")) {
                ToastUtils.showShortToast("请先安装微信");
                return;
            }
            final IWXAPI api = WXAPIFactory.createWXAPI(activity, AppConstant.WX_APP_ID);
            JSONObject jsonObject = new JSONObject(json);
            final String img = jsonObject.getString("img");
            final String link = jsonObject.getString("link");
            String type = jsonObject.getString("type");
            if ("wechat".equals(type)) {
                /**
                 *
                 * @param
                 * weburl- 网页URL
                 * 图片本地路径 -imgurl
                 * 网页标题 -title
                 * 网页内容摘要 -description
                 * sendtype(0:分享到微信好友，1：分享到微信朋友圈)
                 */
                //微信好友分享
                WXWebpageObject webpage = new WXWebpageObject();
                webpage.webpageUrl = link;
                WXMediaMessage msg = new WXMediaMessage(webpage);
                msg.title = "和顺商圈";
                msg.description = "和顺商圈";
//                Bitmap bmp = BitmapFactory.decodeFile(imgurl);
//                Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
//                msg.setThumbImage(thumbBmp);
//                bmp.recycle();
                SendMessageToWX.Req req = new SendMessageToWX.Req();
//                req.transaction = buildTransaction("webpage");
                req.message = msg;
                req.scene = 0;
                api.sendReq(req);

            } else {
                //微信朋友圈分享
                WXWebpageObject webpage = new WXWebpageObject();
                webpage.webpageUrl = link;
                WXMediaMessage msg = new WXMediaMessage(webpage);
                msg.title = "和顺商圈";
                msg.description = "和顺商圈";
                Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.icon_launcher);
                Bitmap.createScaledBitmap(bitmap, 121, 120, true);
                msg.setThumbImage(bitmap);
                bitmap.recycle();
                SendMessageToWX.Req req = new SendMessageToWX.Req();
//                req.transaction = buildTransaction("webpage");
                req.message = msg;
                req.scene = 1;
                api.sendReq(req);
//                OkHttpUtils.get(img)//
//                        .tag("pic")//
//                        .execute(new FileCallback( activity.getExternalCacheDir().getAbsolutePath(), "sharp.jpg") {
//                            @Override
//                            public void onSuccess(final File file, Call call, Response response) {
//                                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//                                Bitmap.createScaledBitmap(bitmap,121,120,true);
//
//                            }
//                        });


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public Bitmap returnBitMap(final String url) {
        final Bitmap[] bitmap = {null};
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL imageurl = null;

                try {
                    imageurl = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    HttpURLConnection conn = (HttpURLConnection) imageurl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap[0] = BitmapFactory.decodeStream(is);
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return Bitmap.createScaledBitmap(bitmap[0], 121, 120, true);
    }


    @JavascriptInterface
    public void appShare(String json) {
        WXShare(json);
//        try {
//            if (!checkPackage("com.tencent.mm")) {
//                ToastUtils.showShortToast("请先安装微信");
//                return;
//            }
//
//            JSONObject jsonObject = new JSONObject(json);
//            String img = jsonObject.getString("img");
//            String link = jsonObject.getString("link");
//            String type = jsonObject.getString("type");
//            if ("wechat".equals(type)) {
//
//                //微信好友分享
//                Wechat.ShareParams sp = new Wechat.ShareParams();
//                sp.setTitle("和顺商圈");
//                sp.setText("和顺商圈");
//                sp.setTitleUrl(link); // 标题的超链接
//                sp.setImagePath(img);
//                sp.setShareType(Platform.SHARE_WEBPAGE);
//                sp.setUrl(link);
//                Platform wx = ShareSDK.getPlatform(Wechat.NAME);
//                wx.setPlatformActionListener(this); // 设置分享事件回调
//                // 执行图文分享
//                wx.share(sp);
//
////                    Platform platform_wxFriend = ShareSDK.getPlatform(Wechat.NAME);
////                    Platform.ShareParams sp = new Platform.ShareParams();
////                    sp.setShareType(Platform.SHARE_WEBPAGE);// 一定要设置分享属性
////                    sp.setUrl(link);
////                    sp.setImageData(null);
////                    sp.setImageUrl(img);
////                    sp.setImagePath(null);
////                    platform_wxFriend.setPlatformActionListener(this); // 设置分享事件回调
////                    platform_wxFriend.share(sp);
//            } else {
//
//                //微信朋友圈分享
//                Wechat.ShareParams sp = new Wechat.ShareParams();
//                sp.setTitle("和顺商圈");
//                sp.setText("和顺商圈");
//                sp.setTitleUrl(link); // 标题的超链接
//                sp.setImagePath(img);
//                sp.setShareType(Platform.SHARE_WEBPAGE);
//                sp.setUrl(link);
//                Platform wx = ShareSDK.getPlatform(WechatMoments.NAME);
//                wx.setPlatformActionListener(this); // 设置分享事件回调
//                // 执行图文分享
//                wx.share(sp);
////                Platform platform_wxFriend = ShareSDK.getPlatform(WechatMoments.NAME);
////                Platform.ShareParams sp = new Platform.ShareParams();
////                sp.setShareType(Platform.SHARE_WEBPAGE);// 一定要设置分享属性
////                sp.setUrl(link);
////                sp.setImageData(null);
////                sp.setImageUrl(img);
////                sp.setImagePath(null);
////                platform_wxFriend.setPlatformActionListener(this); // 设置分享事件回调
////                platform_wxFriend.share(sp);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        ToastUtils.showShortToast("onComplete");
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        ToastUtils.showShortToast("onError");
    }

    @Override
    public void onCancel(Platform platform, int i) {
        ToastUtils.showShortToast("onCancel");

    }
}
