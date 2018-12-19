package com.astgo.naoxuanfeng.video;

import java.util.List;

/**
 * Created by ast009 on 2018/4/18.
 */

public class BannerVidoListBean {

    /**
     * msg : success
     * info : success
     * code : 1
     * status : 1
     * data : [{"id":"11","img_url":"http://hssq.dykj168.com/Uploads/carouselimg/5acc2026c8b8a.png","url":"www.baidu.com"},{"id":"12","img_url":"http://hssq.dykj168.com/Uploads/carouselimg/5acc234035d76.png","url":"www.baidu.com"}]
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
         * id : 11
         * img_url : http://hssq.dykj168.com/Uploads/carouselimg/5acc2026c8b8a.png
         * url : www.baidu.com
         */

        private String id;
        private String img_url;
        private String url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

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
    }
}
