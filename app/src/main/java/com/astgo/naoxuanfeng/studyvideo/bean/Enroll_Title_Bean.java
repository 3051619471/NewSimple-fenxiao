package com.astgo.naoxuanfeng.studyvideo.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/11/26.
 */

public class Enroll_Title_Bean {

    /**
     * msg : success
     * info : success
     * code : 1
     * sta : 1
     * data : [{"id":"4","title":"1","pic":"./Uploads/meeting/5bf3f83a36121.jpg","content":"","star_time":"2018年1月1号","end_time":"2018年1月2号","createtime":"1542715450"},{"id":"5","title":"快速记忆法（1）","pic":"./Uploads/meeting/5bf3f9157173f.jpg","content":"%26lt%3Bp%26gt%3B%0D%0A%09%E5%95%8A%E5%95%8A%E5%95%8A%E5%95%8A%E5%95%8A%E5%95%8A%0D%0A%26lt%3B%2Fp%26gt%3B%0D%0A%26lt%3Bp%26gt%3B%0D%0A%09%E5%95%8A%E5%95%8A%E5%95%8A%E5%95%8A%E5%95%8A%0D%0A%26lt%3B%2Fp%26gt%3B","star_time":"2018年1月1号","end_time":"2018年1月2号","createtime":"1542715669"}]
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
         * id : 4
         * title : 1
         * pic : ./Uploads/meeting/5bf3f83a36121.jpg
         * content :
         * star_time : 2018年1月1号
         * end_time : 2018年1月2号
         * createtime : 1542715450
         */

        private String id;
        private String title;
        private String pic;
        private String content;
        private String star_time;
        private String end_time;
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

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getStar_time() {
            return star_time;
        }

        public void setStar_time(String star_time) {
            this.star_time = star_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }
    }
}
