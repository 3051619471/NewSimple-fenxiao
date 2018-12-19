package com.astgo.naoxuanfeng.bean;

/**
 * Created by ast009 on 2018/1/4.
 */

public class AliPayBean {
// "appid": "2017113000272052",
//         "notify_url": "http://dywxgs.7oks.net/payment/alipay/notify_url.php",
//         "orderId": "2018010414371181199637",
//         "partner": "2088012510185740",
//         "price": 0.01,
//         "privateKey": "MIIEogIBAAKCAQEAvbzNe20kXaql3BKtCnq8VJFiswExJt7T9nuKKVfS5CDPJI/ituDGok0loifVlx9l+NR98sH7JGBnxfA7JbtWnZnt8ovxUcygqtXGCQEJXC2nC1eXze6ffuDR7V20OebDsTdKm2T75/kLCqVPp84A4kHHzIYKCDI4OuL3aB0GjqfF0fNDAb6dVkscYQmMVVS7hmTfOsOMMd+SJ5Q8WlD6orpXCH39G3+8m3JgcemJtBfM3c/CyX0/fVBiHfbfgSuna2CNv85yUkCBH0Qken/SpXD26rrC3f0No2fIUo6Vl2UB8/LGHboS/fbaT2F0l8aolLqwQABKWUZs6JpaVFByaQIDAQABAoIBAGcjf6ttW8xS3BAyUsAUkdWCpl1Z84cGr8AXzbXFmM0LjK56TKpOq4tEVoW8E54vojXOBJz2l3dhtGdjvDM5j87iAvfK3KYzjs7un9T7LERgSKKgvdNiuG629UaLnlGvQIdP4A1yqoCh3z+tNwynrdFQIrTp1AA6lFhmUkjVJmBaoFNnkXV3YGNjXvJos7dUpK9s9pxniL4R4sImjxN1cRqO80BjXuibvViabhHBRS1zp3JyU5EySMmtbCtaUHRmSqKkOnxYP/sqgpfugpzhBNfjN859c4VlRoazx2iquZAG829xHKpJCMyttxpX/lh+dm3fRPeizDpqtnGH23UPVeUCgYEA8WGQE7DBb4lO5w1+naG/ieIRoxx25aOZEph+AbNzGbvgZJv6jL/ErUv8OjlVBJTsdHWbo02h2/mwcNmlDpa8/uy748KOsMNhYri/7DCGi8aOHXtkaYy46yQhC6Zo+zUNa7VueBh2LjdVmv8B86uolWV+rRo+OKoscaO6ESCtf5MCgYEAyTqLNFbPkD4x0gSUIwIFrtInDiWoJCl+sZoLC85vpEua+kwZqqL7aRMzneblFRSHMnM20U+mZkfxnoWoeRdIHMdJgIAkryi2XJeAoVLLJvGvJgZn7tC7dVDJwgXVfgXelEA5H9rmH5aAc7/ggh5vWx7sqKmZxWr1GNK7ajNDq5MCgYAnxxwnIom+B7KpAOBMucsGveqKJraxQTSIi/i37wqYww1HhyzS5QdUR6FZNvl7t87/oiHz12dFCPC6qCM7NDwRBpIwBm/dUVVLH4cBNT326tGgqiTx/+65UUrg0JucSjHb/homUGGpXBUPZda8gR1XAVLO0/RNAj4wu/p9uBTfYQKBgA9ci8HsGp5NUSpSNSjS/7IP0vFuiH5yOHzAYLV/BeDJ8MPIyXDQTlcAI0GbncNVNNjyGK4z4WUPeu96Dpln4JZp1D+eCK7iccFxw10NX/rSGmfRt19wyc6z4yMPpPOg6RPyLyoyYLzqlVXAwRfN0PxoyPI7OETs3FcPx0TdMv5HAoGAHqcojZRlRGOJWs1G0Z0YBKhUl6nBNdvg4FBy5V7dndolcvNy6i4w9HYrUdPVmE8kl2Sf+DDrDDXZy1Qvum6egawCsVOIN72v/cw/54Um56UPOPJiwgAS6yxrVmqIyVdqsfxwtchNgzEvBDgyIH6pKQ28KP1GfbEGx1ybJsEcOmg=",
//         "privateKeyJava":
//         "publicKey": ",
//         "return_url": "http://dywxgs.7oks.net/index.php?m=Home&c=user&a=orderdetail&order_id=21",
//         "title": "Haier/海尔  10公斤kg智能变频滚筒全自动洗衣机",
//         "type": "alipay"

    private String type;
    private String appid;
    private String notify_url;
    private String orderId;
    private String partner;
    private String price;
    private String privateKey;
    private String privateKeyJava;
    private String publicKey;
    private String viewurl;
    private String title;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPrivateKeyJava() {
        return privateKeyJava;
    }

    public void setPrivateKeyJava(String privateKeyJava) {
        this.privateKeyJava = privateKeyJava;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getViewurl() {
        return viewurl;
    }

    public void setViewurl(String viewurl) {
        this.viewurl = viewurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "AliPayBean{" +
                "type='" + type + '\'' +
                ", appid='" + appid + '\'' +
                ", notify_url='" + notify_url + '\'' +
                ", orderId='" + orderId + '\'' +
                ", partner='" + partner + '\'' +
                ", price=" + price +
                ", privateKey='" + privateKey + '\'' +
                ", privateKeyJava='" + privateKeyJava + '\'' +
                ", publicKey='" + publicKey + '\'' +
                ", viewurl='" + viewurl + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
