package com.astgo.naoxuanfeng.bean;

import java.util.List;

/**
 * Created by ast009 on 2017/12/1.
 */

public class MessageListBean {
    private List<JpushMessageBean> list;
    private int curPage;
    private int countPage;
    private int status;

    public List<JpushMessageBean> getList() {
        return list;
    }

    public void setList(List<JpushMessageBean> list) {
        this.list = list;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getCountPage() {
        return countPage;
    }

    public void setCountPage(int countPage) {
        this.countPage = countPage;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MessageListBean{" +
                "list=" + list +
                ", curPage=" + curPage +
                ", countPage=" + countPage +
                ", status=" + status +
                '}';
    }
}
