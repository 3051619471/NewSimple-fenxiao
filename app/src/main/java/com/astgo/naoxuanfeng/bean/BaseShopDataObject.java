package com.astgo.naoxuanfeng.bean;

/**
 * Created by ast009 on 2017/11/15.
 */

public class BaseShopDataObject<T>  {
    private int status;
    private String info;
    private T data;

    public BaseShopDataObject(int status, String info, T data) {
        this.status = status;
        this.info = info;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseShopDataObject{" +
                "status=" + status +
                ", info='" + info + '\'' +
                ", data=" + data +
                '}';
    }
}
