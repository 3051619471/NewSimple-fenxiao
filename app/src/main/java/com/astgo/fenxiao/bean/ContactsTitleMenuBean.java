package com.astgo.fenxiao.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/3/1.
 * 联系人界面侧滑多级菜单的数据抽象类
 */
public class ContactsTitleMenuBean {
    private int left_iv;//父列表左侧图片id
    private String tv;//父列表中间的内容
    private int right_iv_n;//父列表右侧关闭时图片id
    private int right_iv_p;//父列表右侧打开时图片id
    private List<ContactsTitleMenuCityBean> children;//父列表对应子列表的数据值

    public ContactsTitleMenuBean(int left_iv, String tv, int right_iv_n, int right_iv_p, List<ContactsTitleMenuCityBean> children) {
        this.left_iv = left_iv;
        this.tv = tv;
        this.right_iv_n = right_iv_n;
        this.right_iv_p = right_iv_p;
        this.children = children;
    }

    public int getLeft_iv() {
        return left_iv;
    }

    public void setLeft_iv(int left_iv) {
        this.left_iv = left_iv;
    }

    public String getTv() {
        return tv;
    }

    public void setTv(String tv) {
        this.tv = tv;
    }

    public int getRight_iv_n() {
        return right_iv_n;
    }

    public void setRight_iv_n(int right_iv_n) {
        this.right_iv_n = right_iv_n;
    }

    public int getRight_iv_p() {
        return right_iv_p;
    }

    public void setRight_iv_p(int right_iv_p) {
        this.right_iv_p = right_iv_p;
    }

    public List<ContactsTitleMenuCityBean> getChildren() {
        return children;
    }

    public void setChildren(List<ContactsTitleMenuCityBean> children) {
        this.children = children;
    }
}
