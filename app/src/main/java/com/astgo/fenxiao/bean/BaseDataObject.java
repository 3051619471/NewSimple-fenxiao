package com.astgo.fenxiao.bean;

import com.google.gson.Gson;

/**
 * Created by ast009 on 2017/11/15.
 */

public class BaseDataObject<T>  {
    private int code;
    private String msg;
    private T data;

    public BaseDataObject(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseDataObject{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public static Object getBaseBean(String jsonStr,Class<?> cla){
        return new Gson().fromJson(jsonStr,cla);
    }
}
