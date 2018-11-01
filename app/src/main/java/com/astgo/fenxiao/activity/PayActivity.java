//package com.astgo.fenxiao.activity;
//
//
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.os.Parcelable;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.alipay.sdk.app.PayTask;
//
//import com.astgo.fenxiao.alipay.OrderInfoUtil2_0;
//import com.astgo.fenxiao.alipay.PayResult;
//import com.blankj.utilcode.utils.ToastUtils;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import com.astgo.fenxiao.R;
//import com.astgo.fenxiao.bean.AliPayBean;
//import com.astgo.fenxiao.bean.AppConstant;
//import com.astgo.fenxiao.video.CommonActivity;
//import com.tencent.mm.opensdk.modelpay.PayReq;
//import com.tencent.mm.opensdk.openapi.IWXAPI;
//import com.tencent.mm.opensdk.openapi.WXAPIFactory;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Map;
//
///**
// * Created by LST on 2017/9/28.
// */
//
//public class PayActivity extends AppCompatActivity {
//    /** 支付宝支付业务：入参app_id */
//    public  String APPID = "";
//
//    /** 支付宝账户登录授权业务：入参pid值 */
//    public  String PID = "";
//    /** 支付宝账户登录授权业务：入参target_id值 */
//    public  String TARGET_ID = "";
//    /** 商户私钥，pkcs8格式 */
//    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
//    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
//    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
//    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
//    /** 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1 */
//    public  String RSA2_PRIVATE ="";
//    public  String RSA_PRIVATE = "";
//    private ImageView titleLeft;
//    private TextView titleTv;
//    //    private View titleLine;
////    private TextView titleRight;
////    private ImageView titleRightIv;
////    private ImageView titleRightIv1;
////    private RelativeLayout baseTitle;
//    private String json;
//
////    @Override
////    public int getLayoutResource() {
////        return R.layout.activity_pay;
////    }
//
//    private String viewUrl="";
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_pay);
//        initView();
//        initData();
//    }
//
//    private void initView() {
//        titleLeft = (ImageView) findViewById(R.id.title_left);
//        titleTv = (TextView) findViewById(R.id.title_tv);
//
//        titleLeft.setImageResource(R.mipmap.icon_back_black);
//        titleLeft.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PayActivity.this.finish();
//            }
//        });
//        titleTv.setText("支付");
//        titleTv.setTextColor(getResources().getColor(R.color.black));
//    }
//
//    private void initData() {
//        json = getIntent().getStringExtra(AppConstant.PAYJSON);
//        if (json != null) {
//            try {
//                JSONObject jsonObject = new JSONObject(json);
//                if (jsonObject.has("type")) {
//                    String type = jsonObject.getString("type");
//                    if ("alipay".equals(type)) { //调起阿里支付
//                        AliPayBean aliPayBean = new Gson().fromJson(json, new TypeToken<AliPayBean>() {}.getType());
//                        if (aliPayBean != null) {
//                            String appId = aliPayBean.getAppid();
//                            String orderId = aliPayBean.getOrderId();
//                            String notifyUrl = aliPayBean.getNotify_url();
//                            String subJect = aliPayBean.getTitle();
//                            String title = aliPayBean.getTitle();
//                            String price = String.valueOf(aliPayBean.getPrice());
//                            String currentTime = getDetailTime(System.currentTimeMillis());
//                            String javaPrivateKey = aliPayBean.getPrivateKeyJava();
//                            viewUrl = aliPayBean.getReturn_url();
//                            if(!TextUtils.isEmpty(appId) && !TextUtils.isEmpty(orderId) &&
//                                    !TextUtils.isEmpty(price) && !TextUtils.isEmpty(javaPrivateKey)){
//                                APPID = appId;
//                                PID = aliPayBean.getPartner();
//                                RSA2_PRIVATE = aliPayBean.getPrivateKey();
//                                alisdkpay(appId, orderId, notifyUrl,subJect, title, price, currentTime, javaPrivateKey);
//                            }
////                            alisdkpay(jsonObject.getString("appid"), jsonObject.getString("orderId"), jsonObject.getString("notify_url"),
////                                    subject, title, jsonObject.getString("price"), getDetailTime(System.currentTimeMillis()), javarsaPrivateKey);
//
//                        }
//                    } else if ("wechat".equals(type)) {//微信支付
//                        wxPay(jsonObject);
//                    }
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//
//    /**
//     * call alipay sdk pay. 调用SDK支付
//     */
//    public void alisdkpay(String appid, String orderno, String notify_url, String subject, String body, String totalfee, String time, String keyprivate) {
//
//        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
//            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
//                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialoginterface, int i) {
//                            //
//                            finish();
//                        }
//                    }).show();
//            return;
//        }
//
//        /**
//         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
//         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
//         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
//         *
//         * orderInfo的获取必须来自服务端；
//         */
//        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
//        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2,);
//        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
//
//        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
//        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
//        final String orderInfo = orderParam + "&" + sign;
//
//        Runnable payRunnable = new Runnable() {
//
//            @Override
//            public void run() {
//                PayTask alipay = new PayTask(PayActivity.this);
//                Map<String, String> result = alipay.payV2(orderInfo, true);
//                Log.i("msp", result.toString());
//
//                Message msg = new Message();
//                msg.what = SDK_PAY_FLAG;
//                msg.obj = result;
//                mHandler.sendMessage(msg);
//            }
//        };
//
//        Thread payThread = new Thread(payRunnable);
//        payThread.start();
//    }
//
//    //微信支付
//    private void wxPay(JSONObject jsonObject) throws JSONException {
//        PayReq req = new PayReq();
//        AppConstant.WX_APP_ID = jsonObject.getString("appid");//将appid写到常量中保存
//        req.appId = AppConstant.WX_APP_ID;//appid号
//        req.partnerId = jsonObject.getString("mch_id");//商户号
//        req.prepayId = jsonObject.getString("prepay_id");//
//        req.nonceStr = jsonObject.getString("nonce_str");
//        req.timeStamp = jsonObject.getString("timestamp");
//        req.packageValue = jsonObject.getString("package");
//        req.sign = jsonObject.getString("sign");
//        IWXAPI api = WXAPIFactory.createWXAPI(this, req.appId);
//        api.registerApp(req.appId);
//        boolean flag = api.sendReq(req);
//        if(!flag){
//            Toast.makeText(this, "调起失败，可能未安卓微信客户端", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    //支付宝支付tag
//    public static final int SDK_PAY_FLAG = 1;
//    //查询终端设备是否存在支付宝认证账户
//    public static final int SDK_CHECK_FLAG = 2;
//    private String oid;
//    public Handler mHandler = new Handler() {
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case SDK_PAY_FLAG: {
//                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
//                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
//                    String resultStatus = payResult.getResultStatus();
//                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
//                    if (TextUtils.equals(resultStatus, "9000")) {
//                        ToastUtils.showLongToast( "支付成功");
//                        if(!TextUtils.isEmpty(viewUrl)){
//                            toWebView(viewUrl);
//                        }
//                    } else {
//                        // 判断resultStatus 为非“9000”则代表可能支付失败
//                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
//                        if (TextUtils.equals(resultStatus, "8000")) {
//                            ToastUtils.showLongToast( "支付结果确认中");
//                            PayActivity.this.finish();
//
//                        } else {
//                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
//                            ToastUtils.showLongToast("支付失败");
//                            PayActivity.this.finish();
//                        }
//                    }
//                    break;
//                }
//                case SDK_CHECK_FLAG: {
//                    Toast.makeText(PayActivity.this, "检查结果为：" + msg.obj,
//                            Toast.LENGTH_SHORT).show();
//                    break;
//                }
//
//            }
//        }
//    };
//
//    private void toWebView(String url) {
//        Intent intent = new Intent(PayActivity.this, CommonActivity.class);
//        intent.putExtra(AppConstant.KEY_WEB_URL,url);
//        startActivity(intent);
//        PayActivity.this.finish();
//    }
//
//
//    /**
//     * 获取详细的日期与时间
//     * 格式yyyy-MM-dd HH:mm:ss
//     *
//     * @return
//     */
//    public String getDetailTime(long time) {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        return format.format(new Date(time));
//    }
//}
