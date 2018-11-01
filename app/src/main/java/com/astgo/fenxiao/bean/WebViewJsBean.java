package com.astgo.fenxiao.bean;

/**
 * Created by ast009 on 2017/11/28.
 */

public class WebViewJsBean {

    private int status;
    private String info;
    private String url;

    public WebViewJsBean(int status, String info, String url) {
        this.status = status;
        this.info = info;
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfor() {
        return info;
    }

    public void setInfor(String info) {
        this.info = info;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "WebViewJsBean{" +
                "status=" + status +
                ", info='" + info + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
