package com.astgo.fenxiao.bean;

import java.util.List;

/**
 * Created by ast009 on 2018/4/17.
 */

public class NewBanner {

    /**
     * msg : success
     * info : success
     * code : 1
     * status : 1
     * data : [{"title":"标题1","imgurl":"http://hssq.dykj168.com/Uploads/carouselimg/5a0d48da3b4bf.png","jumpurl":"http://www.baidu.com"},{"title":"档位商品","imgurl":"http://hssq.dykj168.com/Uploads/carouselimg/5ac3439b29f58.png","jumpurl":"/home/index/lvgoods"},{"title":"标题3","imgurl":"http://hssq.dykj168.com/Uploads/carouselimg/5a0d48a028e85.png","jumpurl":"http://www.baidu.com"},{"title":"标题2","imgurl":"http://hssq.dykj168.com/Uploads/carouselimg/5a0d48fd48b9c.png","jumpurl":"www.baidu.com"},{"title":"标题4","imgurl":"http://hssq.dykj168.com/Uploads/carouselimg/5a0d491c077b1.png","jumpurl":"www.baidu.com"}]
     */

    private String msg;
    private String info;
    private int code;
    private int status;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * title : 标题1
         * imgurl : http://hssq.dykj168.com/Uploads/carouselimg/5a0d48da3b4bf.png
         * jumpurl : http://www.baidu.com
         */

        private String title;
        private String imgurl;
        private String jumpurl;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getJumpurl() {
            return jumpurl;
        }

        public void setJumpurl(String jumpurl) {
            this.jumpurl = jumpurl;
        }
    }
}
