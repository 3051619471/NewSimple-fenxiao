package com.astgo.naoxuanfeng.studyvideo.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/11/23.
 */

public class Video_Study_Bean {


    /**
     * msg : success
     * info : success
     * code : 1
     * sta : 1
     * data : [{"id":"22","cid":"1","title":"【超速一分钟阅3000字特训法】非常学习（1）","url":"http://pin15gk4j.bkt.clouddn.com/videojy/【超速一分钟阅3000字特训法】非常学习（1）.mp4","o":"1","status":"1","createtime":"1542976232"},{"id":"23","cid":"1","title":"【超速一分钟阅3000字特训法】非常学习（2）","url":"http://pin15gk4j.bkt.clouddn.com/videojy/【超速一分钟阅3000字特训法】非常学习（2）.mp4","o":"2","status":"1","createtime":"1542976232"},{"id":"24","cid":"1","title":"【超速一分钟阅3000字特训法】非常学习（3）","url":"http://pin15gk4j.bkt.clouddn.com/videojy/【超速一分钟阅3000字特训法】非常学习（3）.mp4","o":"3","status":"1","createtime":"1542976232"},{"id":"25","cid":"1","title":"【超速一分钟阅3000字特训法】非常学习（4）","url":"http://pin15gk4j.bkt.clouddn.com/videojy/【超速一分钟阅3000字特训法】非常学习（4）.mp4","o":"4","status":"1","createtime":"1542976232"},{"id":"26","cid":"1","title":"【超速一分钟阅3000字特训法】非常学习（5）","url":"http://pin15gk4j.bkt.clouddn.com/videojy/【超速一分钟阅3000字特训法】非常学习（5）.mp4","o":"5","status":"1","createtime":"1542976232"},{"id":"27","cid":"1","title":"【超速一分钟阅3000字特训法】非常学习（6）","url":"http://pin15gk4j.bkt.clouddn.com/videojy/【超速一分钟阅3000字特训法】非常学习（6）.mp4","o":"6","status":"1","createtime":"1542976232"},{"id":"28","cid":"1","title":"【超速一分钟阅3000字特训法】非常学习（7）","url":"http://pin15gk4j.bkt.clouddn.com/videojy/【超速一分钟阅3000字特训法】非常学习（7）.mp4","o":"7","status":"1","createtime":"1542976232"},{"id":"29","cid":"1","title":"【超速一分钟阅3000字特训法】非常学习（8）","url":"http://pin15gk4j.bkt.clouddn.com/videojy/【超速一分钟阅3000字特训法】非常学习（8）.mp4","o":"8","status":"1","createtime":"1542976232"},{"id":"30","cid":"1","title":"抗遗忘快速记忆法1","url":"http://pin15gk4j.bkt.clouddn.com/videojy/抗遗忘快速记忆法1.mp4","o":"9","status":"1","createtime":"1542976232"},{"id":"31","cid":"1","title":"抗遗忘快速记忆法2","url":"http://pin15gk4j.bkt.clouddn.com/videojy/抗遗忘快速记忆法2.mp4","o":"10","status":"1","createtime":"1542976232"},{"id":"32","cid":"1","title":"抗遗忘快速记忆法3","url":"http://pin15gk4j.bkt.clouddn.com/videojy/抗遗忘快速记忆法3.mp4","o":"11","status":"1","createtime":"1542976232"},{"id":"33","cid":"1","title":"抗遗忘快速记忆法4","url":"http://pin15gk4j.bkt.clouddn.com/videojy/超速记忆法【成功岛商学院】抗遗忘快速记忆法_4.mp4","o":"12","status":"1","createtime":"1542976232"}]
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
         * id : 22
         * cid : 1
         * title : 【超速一分钟阅3000字特训法】非常学习（1）
         * url : http://pin15gk4j.bkt.clouddn.com/videojy/【超速一分钟阅3000字特训法】非常学习（1）.mp4
         * o : 1
         * status : 1
         * createtime : 1542976232
         */

        private String id;
        private String cid;
        private String title;
        private String url;
        private String o;
        private String status;
        private String createtime;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }
    }
}
