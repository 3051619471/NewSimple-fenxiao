package com.astgo.naoxuanfeng.classdomel.class_bean;

import java.util.List;

/**
 * Created by Administrator on 2018/11/2.
 */

public class Resource_Bean {


    /**
     * msg : success
     * info : success
     * code : 1
     * status : 1
     * data : [{"id":"218","cid":"16","title":"说谎的小熊下","url":"http://pi812n9cv.bkt.clouddn.com/13.说谎的小熊下-.mp4","o":"1","type":"2","createtime":"1542888093","status":"1"},{"id":"219","cid":"16","title":"小马过河","url":"http://pi812n9cv.bkt.clouddn.com/14.小马过河-.mp4","o":"2","type":"2","createtime":"1542888093","status":"2"},{"id":"220","cid":"16","title":"井底之蛙","url":"http://pi812n9cv.bkt.clouddn.com/15.井底之蛙-.mp4","o":"3","type":"2","createtime":"1542888093","status":"2"},{"id":"221","cid":"16","title":"蚂蚁报恩","url":"http://pi812n9cv.bkt.clouddn.com/16.蚂蚁报恩-.mp4","o":"4","type":"2","createtime":"1542888093","status":"2"},{"id":"222","cid":"16","title":"孔融让梨","url":"http://pi812n9cv.bkt.clouddn.com/17.孔融让梨-.mp4","o":"5","type":"2","createtime":"1542888093","status":"2"},{"id":"223","cid":"16","title":"狼来了","url":"http://pi812n9cv.bkt.clouddn.com/19.狼来了-.mp4","o":"6","type":"2","createtime":"1542888093","status":"2"},{"id":"224","cid":"16","title":"皇帝的新装上","url":"http://pi812n9cv.bkt.clouddn.com/20.皇帝的新装上-.mp4","o":"7","type":"2","createtime":"1542888093","status":"2"},{"id":"225","cid":"16","title":"狼和小羊","url":"http://pi812n9cv.bkt.clouddn.com/21.狼和小羊-.mp4","o":"8","type":"2","createtime":"1542888093","status":"2"},{"id":"226","cid":"16","title":"抬着驴的父子","url":"http://pi812n9cv.bkt.clouddn.com/22.抬着驴的父子-.mp4","o":"9","type":"2","createtime":"1542888093","status":"2"},{"id":"227","cid":"16","title":"狐狸和乌鸦","url":"http://pi812n9cv.bkt.clouddn.com/23.狐狸和乌鸦-.mp4","o":"10","type":"2","createtime":"1542888093","status":"2"},{"id":"228","cid":"16","title":"皇帝的新装下","url":"http://pi812n9cv.bkt.clouddn.com/24.皇帝的新装下-.mp4","o":"11","type":"2","createtime":"1542888093","status":"2"},{"id":"229","cid":"16","title":"磨杵成针","url":"http://pi812n9cv.bkt.clouddn.com/25.磨杵成针-.mp4","o":"12","type":"2","createtime":"1542888093","status":"2"},{"id":"230","cid":"16","title":"狐假虎威","url":"http://pi812n9cv.bkt.clouddn.com/26.狐假虎威-.mp4","o":"13","type":"2","createtime":"1542888093","status":"2"},{"id":"231","cid":"16","title":"愚公移山","url":"http://pi812n9cv.bkt.clouddn.com/29.愚公移山-.mp4","o":"14","type":"2","createtime":"1542888093","status":"2"},{"id":"232","cid":"16","title":"三只小猪上","url":"http://pi812n9cv.bkt.clouddn.com/3.三只小猪上-.mp4","o":"15","type":"2","createtime":"1542888093","status":"2"},{"id":"233","cid":"16","title":"盲人摸象","url":"http://pi812n9cv.bkt.clouddn.com/32.盲人摸象-.mp4","o":"16","type":"2","createtime":"1542888093","status":"2"},{"id":"234","cid":"16","title":"拔苗助长","url":"http://pi812n9cv.bkt.clouddn.com/37.拔苗助长-.mp4","o":"17","type":"2","createtime":"1542888093","status":"2"},{"id":"235","cid":"16","title":"龟兔赛跑","url":"http://pi812n9cv.bkt.clouddn.com/5.龟兔赛跑-.mp4","o":"18","type":"2","createtime":"1542888093","status":"2"},{"id":"236","cid":"16","title":"小猫钓鱼","url":"http://pi812n9cv.bkt.clouddn.com/6.小猫钓鱼-.mp4","o":"19","type":"2","createtime":"1542888093","status":"2"},{"id":"237","cid":"16","title":"乌鸦喝水","url":"http://pi812n9cv.bkt.clouddn.com/7.乌鸦喝水-.mp4","o":"20","type":"2","createtime":"1542888093","status":"2"},{"id":"238","cid":"16","title":"三只小猪下","url":"http://pi812n9cv.bkt.clouddn.com/8.三只小猪下-.mp4","o":"21","type":"2","createtime":"1542888093","status":"2"}]
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
         * id : 218
         * cid : 16
         * title : 说谎的小熊下
         * url : http://pi812n9cv.bkt.clouddn.com/13.说谎的小熊下-.mp4
         * o : 1
         * type : 2
         * createtime : 1542888093
         * status : 1
         */

        private String id;
        private String cid;
        private String title;
        private String url;
        private String o;
        private String type;
        private String createtime;
        private String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
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

        public String getO() {
            return o;
        }

        public void setO(String o) {
            this.o = o;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
