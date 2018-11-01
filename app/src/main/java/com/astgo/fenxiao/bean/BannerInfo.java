package com.astgo.fenxiao.bean;



/**
 * 轮播图片信息模块的数据实体
 * @author Administrator
 *
 */
public class BannerInfo {
    private String name;
    private String img_url;
    private String url;

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String bannerImage;
    private String bannerLink;

    public BannerInfo(String bannerImage, String bannerLink) {
        this.bannerImage = bannerImage;
        this.bannerLink = bannerLink;
    }

    public String getBannerImage() {
        return bannerImage;
    }
    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }
    public String getBannerLink() {
        return bannerLink;
    }
    public void setBannerLink(String bannerLink) {
        this.bannerLink = bannerLink;
    }

    @Override
    public String toString() {
        return "BannerInfo{" +
                "name='" + name + '\'' +
                ", img_url='" + img_url + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
