package com.astgo.naoxuanfeng.bean;

/**
 * Created by ast009 on 2017/12/13.
 */

public class CommodityBean {
//    http://taobaobuy.7oks.net/index.php?m=Home&c=Index&a=search&keywords=搜索内容

//    商品列表{coupon:优惠券可抵扣,img:商品图片,price:商品原价,sale:销量,title:商品标题,url:跳转链接}
    private String coupon;
    private String img;
    private String price;
    private String sale;
    private String title;
    private String url;

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    @Override
    public String toString() {
        return "CommodityBean{" +
                "coupon='" + coupon + '\'' +
                ", img='" + img + '\'' +
                ", price=" + price +
                ", sale=" + sale +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
