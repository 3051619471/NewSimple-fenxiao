package com.astgo.naoxuanfeng.studyvideo.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/11/26.
 */

public class Video_Banner_Bean {

    /**
     * msg : success
     * info : success
     * code : 1
     * sta : 1
     * data : [{"id":"11","title":"视频轮播1","imgurl":"./Uploads/carouselimg/5b839d6680181.jpg","orders":"0","status":"1","type":"2","jumpurl":"http://ai.dykj168.com","createtime":"1523327014"}]
     */

    private String msg;
    private String info;
    private int code;
    private int sta;
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

    public int getSta() {
        return sta;
    }

    public void setSta(int sta) {
        this.sta = sta;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 11
         * title : 视频轮播1
         * imgurl : ./Uploads/carouselimg/5b839d6680181.jpg
         * orders : 0
         * status : 1
         * type : 2
         * jumpurl : http://ai.dykj168.com
         * createtime : 1523327014
         */

        private String id;
        private String title;
        private String imgurl;
        private String orders;
        private String status;
        private String type;
        private String jumpurl;
        private String createtime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

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

        public String getOrders() {
            return orders;
        }

        public void setOrders(String orders) {
            this.orders = orders;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getJumpurl() {
            return jumpurl;
        }

        public void setJumpurl(String jumpurl) {
            this.jumpurl = jumpurl;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }
    }
}
