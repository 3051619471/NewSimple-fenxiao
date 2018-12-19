//package com.pomelorange.newphonebooks.tools.pay;
//
//import android.os.Message;
//
//import com.alipay.sdk.app.PayTask;
//import com.blankj.utilcode.utils.LogUtils;
//import com.pomelorange.newphonebooks.activity.PayActivity;
//import com.pomelorange.newphonebooks.alipay.OrderInfoUtil2_0;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Map;
//
///**
// * Created by ast009 on 2017/12/4.
// */
//
//public class PayUtils {
//
//    //支付宝支付tag
//    public static final int SDK_PAY_FLAG = 1;
//    //查询终端设备是否存在支付宝认证账户
//    public static final int SDK_CHECK_FLAG = 2;
//    private String oid;
//
//    public static void pay(String json){
//
//        if (json != null) {
//            try {
//                JSONObject jsonObject = new JSONObject(json);
//                if (jsonObject.has("type")) {
//                    String type = jsonObject.getString("type");
//                    if ("alipay".equals(type)) { //调起阿里支付
//                        if (jsonObject.has("appid") && jsonObject.has("orderId")
//                                && jsonObject.has("notify_url") && jsonObject.has("price")
//                                && jsonObject.has("privateKeyJava")) {
//                            String javarsaPrivateKey = jsonObject.getString("privateKeyJava");
//                            String title = "测试数据";
//                            String subject ="话费充值";
//                            alisdkpay(jsonObject.getString("appid"), jsonObject.getString("orderId"), jsonObject.getString("notify_url"),
//                                    subject, title, jsonObject.getString("price"), getDetailTime(System.currentTimeMillis()), javarsaPrivateKey);
//
//                        }
////                    }else if("unionpay".equals(type)){//银联支付
////                        if(jsonObject.has("url")){//跳转银联支付界面
////                            WebServiceUtil.transNewActivity(this, jsonObject.getString("url"), "银联支付", PayUnionWebActivity.class);
////                        }
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
//
//        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(appid, false, orderno, totalfee, subject,body, notify_url, time);
//        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
//        String sign = OrderInfoUtil2_0.getSign(params, keyprivate, false);
//        final String payInfo = orderParam + "&" + sign;
//        LogUtils.i("payInfo:" + payInfo);
//        Runnable payRunnable = new Runnable() {
//            @Override
//            public void run() {
//                // 构造PayTask 对象
//                PayTask alipay = new PayTask(PayActivity.this);
//                // 调用支付接口，获取支付结果
//                Map<String, String> stringMap = alipay.payV2(payInfo, true);
//                Message msg = new Message();
//                msg.what = SDK_PAY_FLAG;
//                msg.obj = stringMap;
//                mHandler.sendMessage(msg);
//            }
//        };
//
//        // 必须异步调用
//        Thread payThread = new Thread(payRunnable);
//        payThread.start();
//    }
//    public static void wxPay(String json){
//
//
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
