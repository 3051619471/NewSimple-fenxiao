package com.astgo.naoxuanfeng.bean;

/**
 * Created by ast009 on 2017/12/5.
 */

public class LoginBean {

    /**
     * msg : success
     * info : success
     * code : 1
     * sta : 1
     * data : {"token":"ef79ce0588923ac3d4143051e7faa880","uid":"1810"}
     */

    private String msg;
    private String info;
    private int code;
    private int sta;
    private DataBean data;

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
        this.info

                = info;
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * token : ef79ce0588923ac3d4143051e7faa880
         * uid : 1810
         */

        private String token;
        private String uid;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }

}
