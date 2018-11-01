package com.astgo.fenxiao.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ast009 on 2018/5/8.
 */

public class WXPay {

    /**
     * appid : wx4eeaa909776fb55a
     * mch_id : 1503083041
     * nonce_str : YUCKp2uPkuRXc4i1
     * prepay_id : wx08142816301372661f1995ae3598675191
     * sign : 968634B6A4101534935E82620D6E6738
     * timestamp : 1525760896
     * key : heshunshangquanweixinpay20180508
     * package : Sign=WXPay
     * type : wechat
     * viewurl : http://hssq.dykj168.com/index.php?m=Home&c=order&a=view&order_id=348
     */

    private String appid;
    private String mch_id;
    private String nonce_str;
    private String prepay_id;
    private String sign;
    private String timestamp;
    private String key;
    @SerializedName("package")
    private String packageX;
    private String type;
    private String viewurl;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getPrepay_id() {
        return prepay_id;
    }

    public void setPrepay_id(String prepay_id) {
        this.prepay_id = prepay_id;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getViewurl() {
        return viewurl;
    }

    public void setViewurl(String viewurl) {
        this.viewurl = viewurl;
    }
}
