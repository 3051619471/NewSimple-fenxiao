package com.astgo.naoxuanfeng.classdomel.class_bean;

import java.util.List;

/**
 * Created by Administrator on 2018/11/27.
 */

public class Class_Banner_Bean {

    /**
     * msg : success
     * info : success
     * code : 1
     * sta : 1
     * data : [{"id":"15","name":"成语故事","pic":"./Uploads/study/5be0ef99d18af.jpg","pid":"12","o":"4","createtime":"1541209152"},{"id":"14","name":"谚语故事","pic":"./Uploads/study/5be0e7441139b.jpg","pid":"12","o":"3","createtime":"1541208972"},{"id":"13","name":"童话故事","pic":"./Uploads/study/5be266be0d047.jpg","pid":"12","o":"2","createtime":"1541208893"},{"id":"16","name":"伊索寓言","pic":"./Uploads/study/5be0ee88be560.jpg","pid":"12","o":"5","createtime":"1541209328"},{"id":"17","name":"人物故事","pic":"./Uploads/study/5be0ee9169906.jpg","pid":"12","o":"6","createtime":"1541209431"},{"id":"18","name":"一千零一夜","pic":"./Uploads/study/5be266c76f453.jpg","pid":"12","o":"7","createtime":"1541209485"}]
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
         * id : 15
         * name : 成语故事
         * pic : ./Uploads/study/5be0ef99d18af.jpg
         * pid : 12
         * o : 4
         * createtime : 1541209152
         */

        private String id;
        private String name;
        private String pic;
        private String pid;
        private String o;
        private String createtime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getO() {
            return o;
        }

        public void setO(String o) {
            this.o = o;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }
    }
}
