package com.astgo.naoxuanfeng.studyvideo.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/11/26.
 */

public class My_Enroll_Bean {

    /**
     * msg : success
     * info : success
     * code : 1
     * sta : 1
     * data : [{"id":"13","mid":"4","uid":"1811","nickname":" 113","name":"大大哥哥哥","phone":"15","createtime":"0","title":"1"},{"id":"14","mid":"4","uid":"1811","nickname":" 113","name":"大大哥哥哥","phone":"15","createtime":"0","title":"1"},{"id":"16","mid":"4","uid":"1811","nickname":" 113","name":"大大哥哥哥","phone":"15","createtime":"0","title":"1"},{"id":"17","mid":"4","uid":"1811","nickname":" 113","name":"小弟弟","phone":"1006","createtime":"0","title":"1"}]
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
         * id : 13
         * mid : 4
         * uid : 1811
         * nickname :  113
         * name : 大大哥哥哥
         * phone : 15
         * createtime : 0
         * title : 1
         */

        private String id;
        private String mid;
        private String uid;
        private String nickname;
        private String name;
        private String phone;
        private String createtime;
        private String title;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMid() {
            return mid;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
