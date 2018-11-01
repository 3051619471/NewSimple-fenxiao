package com.astgo.fenxiao.video;

import java.util.List;

/**
 * Created by ast009 on 2018/4/18.
 */

public class ImageBottomAD {


    /**
     * msg : success
     * info : success
     * code : 1
     * status : 1
     * data : [{"id":"8","img_url":"http://hssq.dykj168.com/Uploads/advs/5acc64f41ac09.png","url":"http://www.baidu.com","title":"底部广告2"},{"id":"5","img_url":"http://hssq.dykj168.com/Uploads/advs/5acc62216b7b2.png","url":"http://www.163.com","title":"广告1"}]
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
         * id : 8
         * img_url : http://hssq.dykj168.com/Uploads/advs/5acc64f41ac09.png
         * url : http://www.baidu.com
         * title : 底部广告2
         */

        private String id;
        private String img_url;
        private String url;
        private String title;

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
