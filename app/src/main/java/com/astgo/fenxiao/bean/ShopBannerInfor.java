package com.astgo.fenxiao.bean;

/**
 * Created by ast009 on 2017/12/13.
 */

public class ShopBannerInfor {

//    imgurl:图片地址,title:名称,jumpurl:跳转链接

    private String imgurl;
    private String title;
    private String jumpurl;

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getJumpurl() {
        return jumpurl;
    }

    public void setJumpurl(String jumpurl) {
        this.jumpurl = jumpurl;
    }

    @Override
    public String toString() {
        return "ShopBannerInfor{" +
                "imgurl='" + imgurl + '\'' +
                ", title='" + title + '\'' +
                ", jumpurl='" + jumpurl + '\'' +
                '}';
    }
}
