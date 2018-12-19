package com.astgo.naoxuanfeng.classdomel.class_bean;

import java.util.List;

/**
 * Created by Administrator on 2018/11/2.
 */

public class Shop_Bean {

    /**
     * msg : success
     * info : success
     * code : 1
     * status : 1
     * data : [{"id":"8","name":"故事","pic":"./Uploads/study/5bc1a5f224364.jpg","pid":"0","o":"1","createtime":"1539417586"},{"id":"10","name":"儿歌","pic":"./Uploads/study/5bc1a644f3068.jpg","pid":"0","o":"4","createtime":"1539417668"}]
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
         * name : 故事
         * pic : ./Uploads/study/5bc1a5f224364.jpg
         * pid : 0
         * o : 1
         * createtime : 1539417586
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
