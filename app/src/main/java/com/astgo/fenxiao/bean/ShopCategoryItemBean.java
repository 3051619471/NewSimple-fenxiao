package com.astgo.fenxiao.bean;

/**
 * Created by ast009 on 2017/12/13.
 */

public class ShopCategoryItemBean {

//    id:id,img:图片地址,title:名称,url:跳转链接
    private String id;
    private String img;
    private String title;
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "ShopCategoryItemBean{" +
                "id='" + id + '\'' +
                ", img='" + img + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
