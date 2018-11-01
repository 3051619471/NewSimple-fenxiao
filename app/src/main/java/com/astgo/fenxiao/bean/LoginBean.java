package com.astgo.fenxiao.bean;

/**
 * Created by ast009 on 2017/12/5.
 */

public class LoginBean {


    /**
     * msg : success
     * info : success
     * code : 1
     * status : 1
     * data : {"token":"6c034f5a5d77b4f253a850dc045f4ac8"}
     */

    private String msg;
    private String info;
    private int code;
    private int status;
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * token : 6c034f5a5d77b4f253a850dc045f4ac8
         */

        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
