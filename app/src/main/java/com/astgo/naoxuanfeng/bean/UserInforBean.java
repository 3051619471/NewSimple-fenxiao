package com.astgo.naoxuanfeng.bean;

/**
 * Created by ast009 on 2017/11/24.
 */

public class UserInforBean {
//    name		昵称
//    phone		电话
//    balance		余额
//    expireddate 有效期
//    activedate  激活日期
//    parentName  上级昵称
//    parentPhone 上级号码
//    newsUrl     分享地址
//    head		头像

    private String name;
    private String phone;
    private String balance;
    private String expireddate;
    private String activedate;
    private String parentName;
    private String parentPhone;
    private String newsUrl;
    private String head;
    private String shopurl;
    private float commission;
    private int coupon;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getExpireddate() {
        return expireddate;
    }

    public void setExpireddate(String expireddate) {
        this.expireddate = expireddate;
    }

    public String getActivedate() {
        return activedate;
    }

    public void setActivedate(String activedate) {
        this.activedate = activedate;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentPhone() {
        return parentPhone;
    }

    public void setParentPhone(String parentPhone) {
        this.parentPhone = parentPhone;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getShopurl() {
        return shopurl;
    }

    public void setShopurl(String shopurl) {
        this.shopurl = shopurl;
    }

    public float getCommission() {
        return commission;
    }

    public void setCommission(float commission) {
        this.commission = commission;
    }

    public int getCoupon() {
        return coupon;
    }

    public void setCoupon(int coupon) {
        this.coupon = coupon;
    }

    @Override
    public String toString() {
        return "UserInforBean{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", balance='" + balance + '\'' +
                ", expireddate='" + expireddate + '\'' +
                ", activedate='" + activedate + '\'' +
                ", parentName='" + parentName + '\'' +
                ", parentPhone='" + parentPhone + '\'' +
                ", newsUrl='" + newsUrl + '\'' +
                ", head='" + head + '\'' +
                ", shopurl='" + shopurl + '\'' +
                ", commission=" + commission +
                ", coupon=" + coupon +
                '}';
    }
}
