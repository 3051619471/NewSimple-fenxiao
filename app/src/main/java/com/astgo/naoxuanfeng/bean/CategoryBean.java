package com.astgo.naoxuanfeng.bean;

import java.util.List;

/**
 * Created by ast009 on 2018/4/17.
 */

public class CategoryBean {

    /**
     * msg : success
     * info : success
     * code : 1
     * status : 1
     * data : [{"id":"1","title":"女装","img":"http://hssq.dykj168.com/Uploads/catimg/5a1e0ba778d18.png","url":"http://hssq.dykj168.com/index.php?m=Home&c=index&a=search&category=1"},{"id":"9","title":"男装","img":"http://hssq.dykj168.com/Uploads/catimg/5a1e0b6bda217.png","url":"http://hssq.dykj168.com/index.php?m=Home&c=index&a=search&category=9"},{"id":"10","title":"内衣","img":"http://hssq.dykj168.com/Uploads/catimg/5a1e0b62f1719.png","url":"http://hssq.dykj168.com/index.php?m=Home&c=index&a=search&category=10"},{"id":"2","title":"母婴","img":"http://hssq.dykj168.com/Uploads/catimg/5a1e0be525f06.png","url":"http://hssq.dykj168.com/index.php?m=Home&c=index&a=search&category=2"},{"id":"3","title":"化妆品","img":"http://hssq.dykj168.com/Uploads/catimg/5a1e0bf4a039d.png","url":"http://hssq.dykj168.com/index.php?m=Home&c=index&a=search&category=3"},{"id":"4","title":"居家","img":"http://hssq.dykj168.com/Uploads/catimg/5a1e0c03cdd92.png","url":"http://hssq.dykj168.com/index.php?m=Home&c=index&a=search&category=4"},{"id":"5","title":"鞋包配饰","img":"http://hssq.dykj168.com/Uploads/catimg/5a1e0c137eb8a.png","url":"http://hssq.dykj168.com/index.php?m=Home&c=index&a=search&category=5"},{"id":"6","title":"美食","img":"http://hssq.dykj168.com/Uploads/catimg/5a1e0c203ac8a.png","url":"http://hssq.dykj168.com/index.php?m=Home&c=index&a=search&category=6"},{"id":"7","title":"文体车品","img":"http://hssq.dykj168.com/Uploads/catimg/5a1e0c2e906ef.png","url":"http://hssq.dykj168.com/index.php?m=Home&c=index&a=search&category=7"},{"id":"8","title":"数码家电","img":"http://hssq.dykj168.com/Uploads/catimg/5a1e0b735a0fa.png","url":"http://hssq.dykj168.com/index.php?m=Home&c=index&a=search&category=8"}]
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
         * id : 1
         * title : 女装
         * img : http://hssq.dykj168.com/Uploads/catimg/5a1e0ba778d18.png
         * url : http://hssq.dykj168.com/index.php?m=Home&c=index&a=search&category=1
         */

        private String id;
        private String title;
        private String img;
        private String url;

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

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
